package com.hyunjung.aiku.core.data.repository

import android.content.Context
import com.hyunjung.aiku.core.datastore.AikuPreferencesDataSource
import com.hyunjung.aiku.core.domain.repository.UserAuthRepository
import com.hyunjung.aiku.core.model.AuthTokens
import com.hyunjung.aiku.core.model.SocialType
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
    private val aikuPreferencesDataSource: AikuPreferencesDataSource,
) : UserAuthRepository {

    override val isLoggedIn: Flow<Boolean> = aikuPreferencesDataSource.userAuthData.map {
        it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()
    }

    override fun login(context: Context, socialType: SocialType): Flow<AuthTokens> = flow {
        val idToken = connectSocialAccount(context, socialType)
        val authTokens = authRemoteDataSource.loginWithSocial(
            socialType = socialType,
            idToken = idToken
        )
        aikuPreferencesDataSource.setCredentials(authTokens, socialType)
        emit(authTokens)
    }

    override suspend fun logout() {
        val socialType = aikuPreferencesDataSource.userAuthData.first().socialType
        if (socialType == null) return

        when (socialType) {
            SocialType.KAKAO -> kakao.logout()
        }
        aikuPreferencesDataSource.clearCredentials()
    }

    private suspend fun connectSocialAccount(
        context: Context,
        type: SocialType
    ): String = when (type) {
        SocialType.KAKAO -> kakao.login(context)
    }
}
