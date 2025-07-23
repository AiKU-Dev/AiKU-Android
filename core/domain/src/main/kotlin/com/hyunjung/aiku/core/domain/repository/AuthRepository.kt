package com.hyunjung.aiku.core.domain.repository

import android.content.Context
import com.hyunjung.aiku.core.model.auth.SignUpForm
import com.hyunjung.aiku.core.model.auth.SocialSignInResult
import com.hyunjung.aiku.core.model.auth.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isSignedIn: Flow<Boolean>

    fun signIn(socialType: SocialType, idToken: String): Flow<Boolean>

    fun connectSocialAccount(context: Context, type: SocialType): Flow<SocialSignInResult>

    fun checkNicknameDuplicated(nickname: String): Flow<Boolean>

    suspend fun signOut()

    suspend fun signUp(signUpForm: SignUpForm): Flow<Unit>
}