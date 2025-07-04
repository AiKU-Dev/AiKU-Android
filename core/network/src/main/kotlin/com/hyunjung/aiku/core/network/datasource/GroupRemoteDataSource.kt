package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupOverview

interface GroupRemoteDataSource {
    suspend fun getGroups(page: Int): List<GroupOverview>
    suspend fun getGroupById(id: Long): GroupDetail
    suspend fun addGroup(name: String): Result<Unit>
}