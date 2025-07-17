package com.hyunjung.aiku.core.domain.repository

import android.content.Context
import com.hyunjung.aiku.core.model.SocialType
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    val isLoggedIn: Flow<Boolean>
    fun login(context: Context, socialType: SocialType): Flow<Boolean>
    suspend fun logout()
}