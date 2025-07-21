package com.hyunjung.aiku.core.auth.token

import com.hyunjung.aiku.core.auth.token.model.AuthTokens
import kotlinx.coroutines.flow.Flow

interface TokenManager {
    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    suspend fun updateTokens(): AuthTokens
}