package com.hyunjung.aiku.core.network.datasource

import android.content.Context

interface SocialAuthDataSource {
    suspend fun login(context: Context): String
    suspend fun logout()
}