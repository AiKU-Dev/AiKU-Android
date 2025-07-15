package com.hyunjung.aiku.core.data.token

import com.hyunjung.aiku.core.auth.TokenManager
import com.hyunjung.aiku.core.datastore.AikuPreferencesDataSource
import com.hyunjung.aiku.core.model.AuthTokens
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultTokenManager @Inject constructor(
    private val aikuPreferencesDatasource: AikuPreferencesDataSource,
) : TokenManager {
    override suspend fun getAccessToken(): String =
        aikuPreferencesDatasource.userAuthData.first().accessToken

    override suspend fun getRefreshToken(): String =
        aikuPreferencesDatasource.userAuthData.first().refreshToken

    override suspend fun setTokens(accessToken: String, refreshToken: String) =
        aikuPreferencesDatasource.setTokens(AuthTokens(accessToken, refreshToken))
}