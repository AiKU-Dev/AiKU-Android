package com.hyunjung.aiku.core.auth

interface TokenManager {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun setTokens(accessToken:String, refreshToken: String)
}