package com.hyunjung.aiku.core.data.dto

import android.os.Build
import com.hyunjung.aiku.core.data.model.GroupOverview
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

fun GroupOverviewResponse.toGroupOverview(): GroupOverview {
    return GroupOverview(
        groupId = groupId,
        groupName = groupName,
        memberSize = memberSize,
        lastScheduleTime = lastScheduleTime?.let {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
                } else null
            } catch (e: Exception) {
                null
            }
        }
    )
}