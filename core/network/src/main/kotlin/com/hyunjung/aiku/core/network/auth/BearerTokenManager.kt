package com.hyunjung.aiku.core.network.auth

import io.ktor.client.plugins.auth.providers.BearerTokens
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BearerTokenManager @Inject constructor() {
    // todo : 임시 토큰 값 설정. KAKAO API 연동 후 변경할 것.
    var token: BearerTokens? = BearerTokens(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXV0aCI6Ik1FTUJFUiIsImV4cCI6MTc1MjczODk3MH0.PK6iGz7_3MgIf7SkAndmYQjTelU8t-26I_sLikTuXdg",
        "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3NTI3Mzg5NzB9.JT1RgIctGnmEfyDcT7fasYt8oW-MwccafFvxH8l_uq8"
    )
        private set

    fun setToken(tokenValue: String, refreshToken: String) {
        token = BearerTokens(tokenValue, refreshToken)
    }
}