package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.auth.token.model.AuthTokens
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.SocialType

interface AuthRemoteDataSource {

    suspend fun signIn(socialType: SocialType, idToken: String): AuthTokens

    suspend fun signUp(signUpForm: SignUpForm)

    suspend fun checkNicknameDuplicated(nickname: String): Boolean

    suspend fun refreshTokens(tokens: AuthTokens): AuthTokens
}