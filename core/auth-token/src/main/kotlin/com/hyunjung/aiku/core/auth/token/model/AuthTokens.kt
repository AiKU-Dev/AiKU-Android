package com.hyunjung.aiku.core.auth.token.model

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)