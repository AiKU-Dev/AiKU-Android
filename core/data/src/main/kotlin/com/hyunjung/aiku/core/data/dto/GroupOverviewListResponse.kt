package com.hyunjung.aiku.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupOverviewListResponse(
    val requestId: String,
    val result: GroupOverviewListResult
)

@Serializable
data class GroupOverviewListResult(
    val page: Int,
    val data: List<GroupOverviewResponse>
)

@Serializable
data class GroupOverviewResponse(
    val groupId: Long,
    val groupName: String,
    val memberSize: Int,
    val lastScheduleTime: String?
)