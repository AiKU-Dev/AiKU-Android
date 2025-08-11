package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.network.di.AuthorizedClient
import com.hyunjung.aiku.core.network.extension.get
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.UserDataResponse
import com.hyunjung.aiku.core.network.model.toModel
import com.hyunjung.aiku.core.network.resource.UserResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import javax.inject.Inject

class KtorUserRemoteDataSource @Inject constructor(
    @AuthorizedClient private val client: HttpClient
) : UserRemoteDataSource {

    override suspend fun getUserData(): UserData =
        client.get(resource = UserResource()).body<ApiResponse<UserDataResponse>>()
            .result.toModel()
}