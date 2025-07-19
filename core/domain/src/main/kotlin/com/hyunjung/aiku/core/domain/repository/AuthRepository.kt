package com.hyunjung.aiku.core.domain.repository

import android.content.Context
import com.hyunjung.aiku.core.model.SocialLoginResult
import com.hyunjung.aiku.core.model.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isLoggedIn: Flow<Boolean>
    fun login(context: Context, socialType: SocialType, idToken: String): Flow<Boolean>
    fun connectSocialAccount(context: Context, type: SocialType): Flow<SocialLoginResult>
    suspend fun logout()
}