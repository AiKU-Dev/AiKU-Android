package com.hyunjung.aiku.core.domain.repository

import androidx.paging.PagingData
import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.JoinedGroup
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroupSummaryPagingData(): Flow<PagingData<JoinedGroup>>
    fun getGroupDetail(id: Long): Flow<GroupDetail>
    suspend fun createGroup(name: String)
}