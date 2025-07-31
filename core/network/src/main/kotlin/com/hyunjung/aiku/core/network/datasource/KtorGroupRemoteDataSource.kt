package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.JoinedGroup
import com.hyunjung.aiku.core.network.di.AuthorizedClient
import com.hyunjung.aiku.core.network.extension.get
import com.hyunjung.aiku.core.network.extension.postJson
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.GroupCreateRequest
import com.hyunjung.aiku.core.network.model.GroupDetailResponse
import com.hyunjung.aiku.core.network.model.GroupOverviewListResult
import com.hyunjung.aiku.core.network.model.toModel
import com.hyunjung.aiku.core.network.resource.GroupResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.setBody
import javax.inject.Inject

class KtorGroupRemoteDataSource @Inject constructor(
    @AuthorizedClient private val client: HttpClient
) : GroupRemoteDataSource {

    override suspend fun getGroupSummaries(page: Int): List<JoinedGroup> =
        client.get(GroupResource(page))
            .body<ApiResponse<GroupOverviewListResult>>()
            .result.data.map { it.toModel() }

    override suspend fun getGroupDetail(id: Long): GroupDetail =
        client.get(GroupResource.Id(id = id))
            .body<ApiResponse<GroupDetailResponse>>()
            .result.toModel()

    override suspend fun createGroup(name: String): Result<Unit> {
        return try {
            client.postJson(GroupResource()) {
                setBody(GroupCreateRequest(name))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}