package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.datastore.UserDataStore
import com.hyunjung.aiku.core.domain.repository.UserDataRepository
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.network.datasource.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DefaultUserDataRepository @Inject constructor(
    private val userDataStore: UserDataStore,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserDataRepository {

    override val userData: Flow<UserData> = userDataStore.userData

    override fun syncUserData(): Flow<Unit> = flow {
        val userData = userRemoteDataSource.getUserData()
        userDataStore.setUserData(userData)
        emit(Unit)
    }
}