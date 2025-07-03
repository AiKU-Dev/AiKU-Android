package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DefaultGroupRepository @Inject constructor(
    private val dataSource: GroupDataSource
) : GroupRepository {

    override fun getGroups(page: Int): Flow<List<GroupOverview>> = flow {
        emit(dataSource.getGroups(page))
    }

    override fun getGroupById(id: Long): Flow<GroupDetail> = flow {
        emit(dataSource.getGroupById(id))
    }

    override suspend fun setGroup(name: String): Result<Unit> =
        dataSource.addGroup(name)
}