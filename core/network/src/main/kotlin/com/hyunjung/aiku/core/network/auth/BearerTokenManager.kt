package com.hyunjung.aiku.core.network.auth

import io.ktor.client.plugins.auth.providers.BearerTokens
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BearerTokenManager @Inject constructor() {
    // todo : 임시 토큰 값 설정. KAKAO API 연동 후 변경할 것.
    var token: BearerTokens? = BearerTokens(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXV0aCI6Ik1FTUJFUiIsImV4cCI6MTc0OTQ2MDY0OH0.OfD3WGWPZvzjwJbanm0VF6isFmG3EVuCqQwXTUHefxQ",
        ""
    )
        private set

    fun setToken(tokenValue: String, refreshToken: String) {
        token = BearerTokens(tokenValue, refreshToken)
    }
}