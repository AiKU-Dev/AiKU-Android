package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupOverviewListResult(
    val page: Int,
    val data: List<GroupOverviewResponse>
)