package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DefaultGroupRepository @Inject constructor(
    private val dataSource: GroupDataSource
) : GroupRepository {

    override fun getGroups(page: Int): Flow<List<GroupOverview>> = flow {
        emit(dataSource.getGroups(page))
    }

    override fun getGroup(id: Long): Flow<GroupDetail> = flow {
        emit(dataSource.getGroup(id))
    }

    override suspend fun setGroup(name: String) {
        dataSource.setGroup(name)
    }
}