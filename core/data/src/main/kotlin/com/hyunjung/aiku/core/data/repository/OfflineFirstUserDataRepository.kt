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

    override suspend fun setUserProfile(nickname: String, profileImage: UserProfileImage) {
        userDataStore.setNickname(nickname)
        userDataStore.setProfileImage(profileImage)
    }
}