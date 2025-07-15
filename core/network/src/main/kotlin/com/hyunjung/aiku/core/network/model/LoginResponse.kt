package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)
