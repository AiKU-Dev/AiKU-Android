package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.datastore.UserDataStore
import com.hyunjung.aiku.core.domain.repository.UserDataRepository
import com.hyunjung.aiku.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    userDataStore: UserDataStore,
) : UserDataRepository {

    override val userData: Flow<UserData> = userDataStore.userData
}