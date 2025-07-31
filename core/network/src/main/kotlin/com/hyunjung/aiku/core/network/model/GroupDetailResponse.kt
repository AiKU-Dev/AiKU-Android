package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.group.GroupDetail
import kotlinx.serialization.Serializable

@Serializable
data class GroupDetailResponse(
    val groupId: Long,
    val groupName: String,
    val members: List<GroupMemberResponse>
)

fun GroupDetailResponse.toModel(): GroupDetail =
    GroupDetail(
        id = groupId,
        name = groupName,
        members = members.map { it.toModel() }
    )

