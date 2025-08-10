package com.hyunjung.aiku.core.domain.repository

import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setUserProfile(nickname: String, profileImage: UserProfileImage)
}