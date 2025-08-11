package com.hyunjung.aiku.core.domain.repository

import com.hyunjung.aiku.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    fun syncUserData(): Flow<Unit>
}