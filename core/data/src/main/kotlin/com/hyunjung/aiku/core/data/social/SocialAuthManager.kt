package com.hyunjung.aiku.core.data.social

import android.content.Context
import com.hyunjung.aiku.core.model.SocialLoginResult

interface SocialAuthManager {

    suspend fun signIn(context: Context): SocialLoginResult

    suspend fun signOut()
}