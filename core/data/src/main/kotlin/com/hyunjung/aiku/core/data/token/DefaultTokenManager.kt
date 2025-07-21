package com.hyunjung.aiku.core.data.token

import com.hyunjung.aiku.core.network.token.TokenManager
import com.hyunjung.aiku.core.datastore.AikuAuthPreferencesDataSource
import com.hyunjung.aiku.core.model.AuthTokens
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultTokenManager @Inject constructor(
    private val aikuAuthPreferencesDatasource: AikuAuthPreferencesDataSource,
) : TokenManager {
    override suspend fun getAccessToken(): String =
        aikuAuthPreferencesDatasource.userAuthData.first().accessToken

    override suspend fun getRefreshToken(): String =
        aikuAuthPreferencesDatasource.userAuthData.first().refreshToken

    override suspend fun setTokens(accessToken: String, refreshToken: String) =
        aikuAuthPreferencesDatasource.setTokens(AuthTokens(accessToken, refreshToken))
}