package com.hyunjung.aiku.core.model

data class UserAuthData(
    val accessToken: String,
    val refreshToken: String,
    val socialType: SocialType?,
)