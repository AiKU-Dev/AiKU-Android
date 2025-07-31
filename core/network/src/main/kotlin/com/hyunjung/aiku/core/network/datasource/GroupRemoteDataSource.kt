package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.JoinedGroup

interface GroupRemoteDataSource {

    suspend fun getJoinedGroups(page: Int): List<JoinedGroup>

    suspend fun getGroupDetail(id: Long): GroupDetail
    
    suspend fun createGroup(name: String): Result<Unit>
}