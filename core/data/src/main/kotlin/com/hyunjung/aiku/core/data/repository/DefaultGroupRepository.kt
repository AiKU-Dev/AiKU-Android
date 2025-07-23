package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.domain.repository.GroupRepository
import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DefaultGroupRepository @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource
) : GroupRepository {

    override fun getGroups(page: Int): Flow<List<GroupSummary>> = flow {
        emit(groupRemoteDataSource.getGroups(page))
    }

    override fun getGroupById(id: Long): Flow<GroupDetail> = flow {
        emit(groupRemoteDataSource.getGroupById(id))
    }

    override suspend fun setGroup(name: String): Result<Unit> =
        groupRemoteDataSource.addGroup(name)
}