package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)
