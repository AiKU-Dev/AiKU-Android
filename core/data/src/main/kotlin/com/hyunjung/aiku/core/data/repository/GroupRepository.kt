package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupOverview
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroups(page: Int): Flow<List<GroupOverview>>
    fun getGroup(id: Long): Flow<GroupDetail>
    suspend fun setGroup(name: String)
}