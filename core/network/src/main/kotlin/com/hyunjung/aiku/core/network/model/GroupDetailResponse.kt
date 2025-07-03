package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.GroupDetail
import kotlinx.serialization.Serializable

@Serializable
data class GroupDetailResponse(
    val groupId: Long,
    val groupName: String,
    val members: List<GroupMemberResponse>
)

fun GroupDetailResponse.toModel(): GroupDetail =
    GroupDetail(
        groupId = groupId,
        groupName = groupName,
        members = members.map { it.toModel() }
    )

