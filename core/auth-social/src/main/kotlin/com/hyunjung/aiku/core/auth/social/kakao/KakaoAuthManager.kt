package com.hyunjung.aiku.core.auth.social.kakao

import android.content.Context
import com.hyunjung.aiku.core.auth.social.SocialAuthException
import com.hyunjung.aiku.core.auth.social.SocialAuthManager
import com.hyunjung.aiku.core.model.SocialSignInResult
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class KakaoAuthManager @Inject constructor(
    private val kakaoUserApiClient: UserApiClient,
) : SocialAuthManager {

    override suspend fun signIn(context: Context): SocialSignInResult =
        suspendCancellableCoroutine { continuation ->
            val callback = { token: OAuthToken?, error: Throwable? ->
                signInCallback(token, error, continuation)
            }


            if (kakaoUserApiClient.isKakaoTalkLoginAvailable(context)) {
                kakaoUserApiClient.loginWithKakaoTalk(context = context, callback = callback)
            } else {
                kakaoUserApiClient.loginWithKakaoAccount(context = context, callback = callback)
            }
        }

    override suspend fun signOut() = suspendCancellableCoroutine { continuation ->
        kakaoUserApiClient.logout { error ->
            if (continuation.isActive) {
                if (error != null) {
                    continuation.resumeWithException(SocialAuthException.Unknown(error))
                } else {
                    continuation.resume(Unit)
                }
            }
        }
    }

    private fun signInCallback(
        token: OAuthToken?,
        error: Throwable?,
        continuation: CancellableContinuation<SocialSignInResult>
    ) {
        if (!continuation.isActive) return

        if (token == null) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                continuation.resumeWithException(SocialAuthException.Cancelled(error))
            } else {
                continuation.resumeWithException(SocialAuthException.Unknown(error))
            }
            return
        }

        val idToken = token.idToken
        if (idToken == null) {
            continuation.resumeWithException(SocialAuthException.MissingIdToken())
            return
        }

        kakaoUserApiClient.me { user, error ->
            if (!continuation.isActive) return@me

            when {
                user?.kakaoAccount?.email != null -> continuation.resume(
                    SocialSignInResult(idToken, user.kakaoAccount!!.email!!)
                )

                error != null -> continuation.resumeWithException(
                    SocialAuthException.UserInfoFetchFailed(
                        error
                    )
                )

                else -> continuation.resumeWithException(SocialAuthException.ProviderError(null))
            }
        }
    }
}