package com.hyunjung.aiku.core.network.ktor

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupOverview
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.GroupDetailResponse
import com.hyunjung.aiku.core.network.model.GroupOverviewListResult
import com.hyunjung.aiku.core.network.model.toModel
import com.hyunjung.aiku.core.network.resource.Groups
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class KtorGroupDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : GroupDataSource {
    override suspend fun getGroups(page: Int): List<GroupOverview> =
        client.get(Groups(page)).body<ApiResponse<GroupOverviewListResult>>()
            .result.data.map { it.toModel() }

    override suspend fun getGroupById(id: Long): GroupDetail =
        client.get(Groups.Id(id = id))
            .body<ApiResponse<GroupDetailResponse>>().result.toModel()

    override suspend fun addGroup(name: String) {
        TODO("Not yet implemented")
    }
}