package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.group.JoinedGroup
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class GroupOverviewResponse(
    val groupId: Long,
    val groupName: String,
    val memberSize: Int,
    val lastScheduleTime: String?
)

fun GroupOverviewResponse.toModel(): JoinedGroup =
    JoinedGroup(
        id = groupId,
        name = groupName,
        memberSize = memberSize,
        lastScheduleTime = lastScheduleTime?.let { LocalDateTime.parse(lastScheduleTime) }
    )