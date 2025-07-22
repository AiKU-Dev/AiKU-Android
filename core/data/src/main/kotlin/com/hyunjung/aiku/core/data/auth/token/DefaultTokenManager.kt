package com.hyunjung.aiku.core.data.auth.token

import com.hyunjung.aiku.core.auth.token.TokenManager
import com.hyunjung.aiku.core.auth.token.model.AuthTokens
import com.hyunjung.aiku.core.datastore.AikuAuthPreferencesDataSource
import com.hyunjung.aiku.core.network.datasource.AuthRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultTokenManager @Inject constructor(
    private val aikuAuthPreferencesDatasource: AikuAuthPreferencesDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : TokenManager {

    override val accessToken: Flow<String> = aikuAuthPreferencesDatasource.accessToken

    override val refreshToken: Flow<String> = aikuAuthPreferencesDatasource.refreshToken

    override suspend fun updateTokens(): AuthTokens {

        val newTokens = authRemoteDataSource.refreshTokens(
            combine(
                accessToken,
                refreshToken
            ) { accessToken, refreshToken -> AuthTokens(accessToken, refreshToken) }.first()
        )

        aikuAuthPreferencesDatasource.setTokens(
            accessToken = newTokens.accessToken,
            refreshToken = newTokens.refreshToken
        )

        return newTokens
    }
}