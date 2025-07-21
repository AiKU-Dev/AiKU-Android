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
import com.hyunjung.aiku.core.network.extension.submitFormWithBinaryData
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.NicknameExistenceResponse
import com.hyunjung.aiku.core.network.model.SignInResponse
import com.hyunjung.aiku.core.network.resource.AuthResource
import com.hyunjung.aiku.core.network.resource.UserResource
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import javax.inject.Inject

class DefaultAuthRemoteDataSource @Inject constructor(
    @UnauthenticatedClient private val unauthenticatedClient: HttpClient,
) : AuthRemoteDataSource {

    override suspend fun signIn(
        socialType: SocialType,
        idToken: String,
    ): AuthTokens = when (socialType) {
        SocialType.KAKAO -> signInWithKakao(idToken)
    }

    override suspend fun signUp(signUpForm: SignUpForm) {
        unauthenticatedClient.submitFormWithBinaryData<UserResource, Unit>(
            resource = UserResource(),
            partsBuilder = {
                formData {
                    appendBaseFields(signUpForm)
                    appendProfileFields(signUpForm.memberProfile)
                    appendAgreementFields(signUpForm.agreedTerms)
                    append("recommenderNickname", signUpForm.recommenderNickname)
                }
            }
        )
    }

    override suspend fun checkNicknameDuplicated(nickname: String): Boolean =
        unauthenticatedClient.get<UserResource.CheckNickname, ApiResponse<NicknameExistenceResponse>>(
            resource = UserResource.CheckNickname(nickname = nickname)
        ).result.exist

    override suspend fun refreshTokens(refreshToken: String): AuthTokens =
        unauthenticatedClient.post<AuthResource.Refresh, ApiResponse<SignInResponse>>(
            resource = AuthResource.Refresh(),
            body = mapOf("refreshToken" to refreshToken),
        ).result.let { AuthTokens(it.accessToken, it.refreshToken) }

    private suspend fun signInWithKakao(
        idToken: String,
    ): AuthTokens =
        unauthenticatedClient.post<AuthResource.SignIn.Kakao, ApiResponse<SignInResponse>>(
            resource = AuthResource.SignIn.Kakao(),
            body = mapOf("idToken" to idToken),
        ).result.let { AuthTokens(it.accessToken, it.refreshToken) }
}