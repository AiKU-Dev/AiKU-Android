package com.hyunjung.aiku.core.data.social

import android.content.Context
import com.hyunjung.aiku.core.model.SocialSignInResult

interface SocialAuthManager {

    suspend fun signIn(context: Context): SocialSignInResult

    suspend fun signOut()
}