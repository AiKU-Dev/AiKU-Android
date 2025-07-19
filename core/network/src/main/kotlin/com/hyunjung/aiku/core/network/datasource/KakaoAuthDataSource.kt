package com.hyunjung.aiku.core.network.datasource

import android.content.Context
import com.hyunjung.aiku.core.model.SocialLoginResult
import com.hyunjung.aiku.core.network.NetworkException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoAuthDataSource @Inject constructor(
    private val kakaoUserApiClient: UserApiClient,
) : SocialAuthDataSource {

    override suspend fun login(context: Context): SocialLoginResult =
        suspendCancellableCoroutine { continuation ->
            val callback = createKakaoLoginCallback(continuation)

            if (kakaoUserApiClient.isKakaoTalkLoginAvailable(context)) {
                kakaoUserApiClient.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        kakaoUserApiClient.loginWithKakaoAccount(
                            context = context,
                            callback = callback
                        )
                    } else {
                        callback(token, null)
                    }
                }
            } else {
                kakaoUserApiClient.loginWithKakaoAccount(context = context, callback = callback)
            }
        }

    override suspend fun logout() =
        suspendCancellableCoroutine { continuation ->
            kakaoUserApiClient.logout { error ->
                if (error != null) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(NetworkException.Unknown)
                    }
                } else {
                    if (continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }
            }
        }

    private fun createKakaoLoginCallback(
        continuation: CancellableContinuation<SocialLoginResult>,
    ): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                val exception =
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        NetworkException.Unauthorized
                    } else {
                        NetworkException.Unknown
                    }
                if (continuation.isActive) continuation.resumeWithException(exception)
            }

            token != null -> {
                token.idToken?.let { idToken ->
                    kakaoUserApiClient.me { user, error ->
                        if (error != null) {
                            if (continuation.isActive) continuation.resumeWithException(
                                NetworkException.Unknown
                            )
                        } else {
                            val email = user?.kakaoAccount?.email
                            if (email != null) {
                                if (continuation.isActive) continuation.resume(
                                    SocialLoginResult(
                                        idToken = idToken,
                                        email = email
                                    )
                                )
                            } else {
                                if (continuation.isActive) continuation.resumeWithException(
                                    NetworkException.Unauthorized
                                )
                            }
                        }
                    }
                } ?: run {
                    if (continuation.isActive) continuation.resumeWithException(
                        NetworkException.Unauthorized
                    )
                }
            }

            else -> {
                if (continuation.isActive) continuation.resumeWithException(NetworkException.Unknown)
            }
        }
    }
}

