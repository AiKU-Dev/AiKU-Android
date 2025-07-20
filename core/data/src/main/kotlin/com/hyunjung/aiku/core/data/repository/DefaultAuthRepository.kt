package com.hyunjung.aiku.core.data.repository

import android.content.Context
import com.hyunjung.aiku.core.datastore.AikuAuthPreferencesDataSource
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.SocialLoginResult
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.network.exception.NetworkException
import com.hyunjung.aiku.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.SocialAuthDataSource
import com.hyunjung.aiku.core.network.di.SocialLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    @SocialLogin(SocialType.KAKAO) private val kakao: SocialAuthDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val aikuAuthPreferencesDataSource: AikuAuthPreferencesDataSource,
) : AuthRepository {

    override val isLoggedIn: Flow<Boolean> = aikuAuthPreferencesDataSource.userAuthData.map {
        it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()
    }

    override fun login(socialType: SocialType, idToken: String): Flow<Boolean> =
        flow {
            try {
                val authTokens = authRemoteDataSource.loginWithSocial(
                    socialType = socialType,
                    idToken = idToken
                )
                aikuAuthPreferencesDataSource.setCredentials(authTokens, socialType)
                emit(true)
            } catch (e: NetworkException.NotFound) {
                emit(false)
            } catch (e: Exception) {
                throw e
            }
        }

    override suspend fun logout() {
        val socialType = aikuAuthPreferencesDataSource.userAuthData.first().socialType
        if (socialType == null) return

        when (socialType) {
            SocialType.KAKAO -> kakao.logout()
        }
        aikuAuthPreferencesDataSource.clearCredentials()
    }

    override suspend fun signUp(signUpForm: SignUpForm): Flow<Unit> = flow {
        authRemoteDataSource.signUp(signUpForm)
        emit(Unit)
    }

    override fun connectSocialAccount(
        context: Context,
        type: SocialType,
    ): Flow<SocialLoginResult> = flow {
        emit(
            when (type) {
                SocialType.KAKAO -> kakao.login(context)
            }
        )
    }

    override fun checkNicknameDuplicated(nickname: String): Flow<Boolean> = flow {
        emit(authRemoteDataSource.checkNicknameDuplicated(nickname))
    }
}
