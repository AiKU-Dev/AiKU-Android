package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.AuthTokens
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.LoginResponse
import com.hyunjung.aiku.core.network.resource.AuthResource
import com.hyunjung.aiku.core.network.util.post
import io.ktor.client.HttpClient

class AuthRemoteDataSourceImpl(
    private val client: HttpClient
) : AuthRemoteDataSource {
    override suspend fun loginWithSocial(
        socialType: SocialType,
        idToken: String,
    ): AuthTokens = when (socialType) {
        SocialType.KAKAO -> loginWithKakao(idToken)
    }

    private suspend fun loginWithKakao(
        idToken: String,
    ): AuthTokens =
        client.post<AuthResource.Kakao, ApiResponse<LoginResponse>>(
            resource = AuthResource.Kakao(),
            body = mapOf("idToken" to idToken),
        ).result.let { AuthTokens(it.accessToken, it.refreshToken) }
}