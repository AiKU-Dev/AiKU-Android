package com.hyunjung.aiku.core.network.datasource

import android.content.Context
import com.hyunjung.aiku.core.model.SocialLoginResult

interface SocialAuthDataSource {
    suspend fun login(context: Context): SocialLoginResult
    suspend fun logout()
}