package com.hyunjung.aiku.core.data.repository

import android.content.Context
import com.hyunjung.aiku.core.datastore.AikuAuthPreferencesDataSource
import com.hyunjung.aiku.core.domain.repository.UserAuthRepository
import com.hyunjung.aiku.core.model.SocialLoginResult
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.network.NetworkException
import com.hyunjung.aiku.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.SocialAuthDataSource
import com.hyunjung.aiku.core.network.di.SocialLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUserAuthRepository @Inject constructor(
    @SocialLogin(SocialType.KAKAO) private val kakao: SocialAuthDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val aikuAuthPreferencesDataSource: AikuAuthPreferencesDataSource,
) : UserAuthRepository {

    override val isLoggedIn: Flow<Boolean> = aikuAuthPreferencesDataSource.userAuthData.map {
        it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()
    }

    override fun login(context: Context, socialType: SocialType, idToken:String): Flow<Boolean> = flow {
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

}
