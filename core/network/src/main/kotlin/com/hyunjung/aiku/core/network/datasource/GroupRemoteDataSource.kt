package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSummary

interface GroupRemoteDataSource {

    suspend fun getGroupSummaries(page: Int): List<GroupSummary>

    suspend fun getGroupDetail(id: Long): GroupDetail
    
    suspend fun createGroup(name: String): Result<Unit>
}