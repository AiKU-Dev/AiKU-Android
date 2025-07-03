package com.hyunjung.aiku.core.network.ktor

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupOverview
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.GroupCreateRequest
import com.hyunjung.aiku.core.network.model.GroupDetailResponse
import com.hyunjung.aiku.core.network.model.GroupOverviewListResult
import com.hyunjung.aiku.core.network.model.toModel
import com.hyunjung.aiku.core.network.resource.Groups
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
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

    override suspend fun addGroup(name: String): Result<Unit> {
        return try {
            client.post(Groups()) {
                contentType(ContentType.Application.Json)
                setBody(GroupCreateRequest(name))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            println("실패 ${e.message}")
            Result.failure(e)
        }
    }
}