package com.hyunjung.aiku.core.domain.repository

import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupOverview
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroups(page: Int): Flow<List<GroupOverview>>
    fun getGroupById(id: Long): Flow<GroupDetail>
    suspend fun setGroup(name: String): Result<Unit>
}