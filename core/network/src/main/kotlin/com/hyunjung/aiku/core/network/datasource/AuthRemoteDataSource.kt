package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.AuthTokens

interface AuthRemoteDataSource {
    suspend fun loginWithSocial(
        idToken:String,
    ): AuthTokens
}