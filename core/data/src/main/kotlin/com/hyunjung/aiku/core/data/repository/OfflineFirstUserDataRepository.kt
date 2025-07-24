package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.datastore.UserDataStore
import com.hyunjung.aiku.core.domain.repository.UserDataRepository
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val userDataStore: UserDataStore,
) : UserDataRepository {

    override val userData: Flow<UserData> = userDataStore.userData

    override suspend fun setEmail(email: String) {
        userDataStore.setEmail(email)
    }

    override suspend fun setNickname(nickname: String) {
        userDataStore.setNickname(nickname)
    }

    override suspend fun setProfileImage(profileImage: UserProfileImage) {
        userDataStore.setProfileImage(profileImage)
    }
}