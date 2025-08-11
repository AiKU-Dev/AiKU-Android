package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.UserData

interface UserRemoteDataSource {

    suspend fun getUserData(): UserData
}