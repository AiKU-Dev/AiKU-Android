package com.hyunjung.aiku.core.data.token

import com.hyunjung.aiku.core.model.AuthTokens

interface TokenManager {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun setTokens(authTokens: AuthTokens)
}