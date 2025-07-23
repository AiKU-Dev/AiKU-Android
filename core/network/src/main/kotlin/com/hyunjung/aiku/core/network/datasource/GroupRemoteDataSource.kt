package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSummary

interface GroupRemoteDataSource {
    suspend fun getGroups(page: Int): List<GroupSummary>
    suspend fun getGroupById(id: Long): GroupDetail
    suspend fun addGroup(name: String): Result<Unit>
}