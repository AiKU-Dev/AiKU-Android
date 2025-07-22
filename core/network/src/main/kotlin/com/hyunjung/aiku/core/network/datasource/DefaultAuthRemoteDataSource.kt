package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.auth.token.model.AuthTokens
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.network.di.UnauthenticatedClient
import com.hyunjung.aiku.core.network.extension.appendAgreementFields
import com.hyunjung.aiku.core.network.extension.appendBaseFields
import com.hyunjung.aiku.core.network.extension.appendProfileFields
import com.hyunjung.aiku.core.network.extension.get
import com.hyunjung.aiku.core.network.extension.post
import com.hyunjung.aiku.core.network.extension.postJson
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.NicknameExistenceResponse
import com.hyunjung.aiku.core.network.model.SignInResponse
import com.hyunjung.aiku.core.network.resource.AuthResource
import com.hyunjung.aiku.core.network.resource.UserResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import javax.inject.Inject

class DefaultAuthRemoteDataSource @Inject constructor(
    @UnauthenticatedClient private val client: HttpClient,
) : AuthRemoteDataSource {

    override suspend fun signIn(
        socialType: SocialType,
        idToken: String,
    ): AuthTokens = when (socialType) {
        SocialType.KAKAO -> signInWithKakao(idToken)
    }

    override suspend fun signUp(signUpForm: SignUpForm) {
        client.post(UserResource()) {
            setBody(MultiPartFormDataContent(formData {
                appendBaseFields(signUpForm)
                appendProfileFields(signUpForm.memberProfile)
                appendAgreementFields(signUpForm.agreedTerms)
                append("recommenderNickname", signUpForm.recommenderNickname)
            }))
        }
    }

    override suspend fun checkNicknameDuplicated(nickname: String): Boolean =
        client.get(UserResource.CheckNickname(nickname = nickname))
            .body<ApiResponse<NicknameExistenceResponse>>()
            .result.exist

    override suspend fun refreshTokens(tokens: AuthTokens): AuthTokens =
        client.postJson(AuthResource.Refresh()) {
            setBody(mapOf("refreshToken" to tokens.refreshToken))
            headers {
                append(HttpHeaders.Authorization, "Bearer ${tokens.accessToken}")
            }
        }
            .body<ApiResponse<SignInResponse>>()
            .result.let { AuthTokens(it.accessToken, it.refreshToken) }

    private suspend fun signInWithKakao(
        idToken: String,
    ): AuthTokens =
        client.postJson(AuthResource.SignIn.Kakao()) {
            setBody(mapOf("idToken" to idToken))
        }
            .body<ApiResponse<SignInResponse>>()
            .result.let { AuthTokens(it.accessToken, it.refreshToken) }
}