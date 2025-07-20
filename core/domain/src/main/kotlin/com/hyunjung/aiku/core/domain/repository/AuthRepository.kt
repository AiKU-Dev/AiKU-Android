package com.hyunjung.aiku.core.domain.repository

import android.content.Context
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.SocialLoginResult
import com.hyunjung.aiku.core.model.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isLoggedIn: Flow<Boolean>

    fun login(socialType: SocialType, idToken: String): Flow<Boolean>

    fun connectSocialAccount(context: Context, type: SocialType): Flow<SocialLoginResult>

    fun checkNicknameDuplicated(nickname: String): Flow<Boolean>

    suspend fun logout()

    suspend fun signUp(signUpForm: SignUpForm): Flow<Unit>
}