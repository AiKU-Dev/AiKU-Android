package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.AuthTokens
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.network.extension.appendAgreementFields
import com.hyunjung.aiku.core.network.extension.appendBaseFields
import com.hyunjung.aiku.core.network.extension.appendProfileFields
import com.hyunjung.aiku.core.network.extension.get
import com.hyunjung.aiku.core.network.extension.post
import com.hyunjung.aiku.core.network.extension.submitFormWithBinaryData
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.SignInResponse
import com.hyunjung.aiku.core.network.model.NicknameExistenceResponse
import com.hyunjung.aiku.core.network.resource.AuthResource
import com.hyunjung.aiku.core.network.resource.UserResource
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import javax.inject.Inject

class DefaultAuthRemoteDataSource @Inject constructor(
    private val client: HttpClient
) : AuthRemoteDataSource {

    override suspend fun signIn(
        socialType: SocialType,
        idToken: String,
    ): AuthTokens = when (socialType) {
        SocialType.KAKAO -> signInWithKakao(idToken)
    }

    override suspend fun signUp(signUpForm: SignUpForm) {
        client.submitFormWithBinaryData<UserResource, Unit>(
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
        client.get<UserResource.CheckNickname, ApiResponse<NicknameExistenceResponse>>(
            resource = UserResource.CheckNickname(nickname = nickname)
        ).result.exist

    private suspend fun signInWithKakao(
        idToken: String,
    ): AuthTokens =
        client.post<AuthResource.Kakao, ApiResponse<SignInResponse>>(
            resource = AuthResource.Kakao(),
            body = mapOf("idToken" to idToken),
        ).result.let { AuthTokens(it.accessToken, it.refreshToken) }
}