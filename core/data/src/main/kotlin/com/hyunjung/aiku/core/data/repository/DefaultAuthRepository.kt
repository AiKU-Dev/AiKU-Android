package com.hyunjung.aiku.core.data.repository

import android.content.Context
import com.hyunjung.aiku.core.auth.social.SocialAuthManager
import com.hyunjung.aiku.core.auth.social.di.SocialAuth
import com.hyunjung.aiku.core.datastore.AuthSessionStore
import com.hyunjung.aiku.core.datastore.UserDataStore
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import com.hyunjung.aiku.core.model.auth.SignUpForm
import com.hyunjung.aiku.core.model.auth.SocialSignInResult
import com.hyunjung.aiku.core.model.auth.SocialType
import com.hyunjung.aiku.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.aiku.core.network.exception.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    @SocialAuth(SocialType.KAKAO) private val kakao: SocialAuthManager,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authSessionStore: AuthSessionStore,
    private val userDataStore: UserDataStore,
) : AuthRepository {

    override val isSignedIn: Flow<Boolean> =
        authSessionStore.accessToken.map { it.isNotEmpty() }

    override fun signIn(socialType: SocialType, idToken: String): Flow<Boolean> = flow {
        try {
            val authTokens = authRemoteDataSource.signIn(
                socialType = socialType,
                idToken = idToken
            )

            authSessionStore.setAuthSession(
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken,
                socialType = socialType
            )

            emit(true)
        } catch (_: NetworkException.NotFound) {
            emit(false)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signOut() {
        val socialType = authSessionStore.socialType.first()
        if (socialType == null) return

        when (socialType) {
            SocialType.KAKAO -> kakao.signOut()
        }

        authSessionStore.clearAuthSession()
    }

    override suspend fun signUp(signUpForm: SignUpForm): Flow<Unit> = flow {
        authRemoteDataSource.signUp(signUpForm)
        userDataStore.setEmail(signUpForm.email)
        userDataStore.setNickname(signUpForm.nickname)
        userDataStore.setProfile(signUpForm.userProfileImage)
        emit(Unit)
    }

    override fun connectSocialAccount(
        context: Context,
        type: SocialType,
    ): Flow<SocialSignInResult> = flow {
        when (type) {
            SocialType.KAKAO -> emit(kakao.signIn(context))
        }
    }

    override fun checkNicknameDuplicated(nickname: String): Flow<Boolean> = flow {
        emit(authRemoteDataSource.checkNicknameDuplicated(nickname))
    }
}
