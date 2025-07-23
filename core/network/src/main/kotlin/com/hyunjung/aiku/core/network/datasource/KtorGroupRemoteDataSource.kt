package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.network.di.AuthorizedClient
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.GroupCreateRequest
import com.hyunjung.aiku.core.network.model.GroupDetailResponse
import com.hyunjung.aiku.core.network.model.GroupOverviewListResult
import com.hyunjung.aiku.core.network.model.toModel
import com.hyunjung.aiku.core.network.resource.GroupResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class KtorGroupRemoteDataSource @Inject constructor(
    @AuthorizedClient private val client: HttpClient
) : GroupRemoteDataSource {
    override suspend fun getGroups(page: Int): List<GroupSummary> =
        client.get(GroupResource(page)).body<ApiResponse<GroupOverviewListResult>>()
            .result.data.map { it.toModel() }

    override suspend fun getGroupById(id: Long): GroupDetail =
        client.get(GroupResource.Id(id = id))
            .body<ApiResponse<GroupDetailResponse>>().result.toModel()

    override suspend fun addGroup(name: String): Result<Unit> {
        return try {
            client.post(GroupResource()) {
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