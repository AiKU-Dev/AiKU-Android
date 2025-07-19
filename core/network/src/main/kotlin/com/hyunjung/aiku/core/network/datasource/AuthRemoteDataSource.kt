package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.AuthTokens
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.SocialType

interface AuthRemoteDataSource {
    suspend fun loginWithSocial(
        socialType: SocialType,
        idToken: String,
    ): AuthTokens

    suspend fun signUp(signUpForm: SignUpForm)
    suspend fun checkNicknameDuplicated(nickname: String): Boolean
}