package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.data.model.Schedule
import com.hyunjung.aiku.core.data.model.ScheduleStatus
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ScheduleResponse(
    val groupId: Long,
    val groupName: String,
    val scheduleId: Long,
    val scheduleName: String,
    val location: LocationResponse,
    val scheduleTime: String,
    val memberSize: Int,
    val scheduleStatus: String
)

fun ScheduleResponse.toModel(): Schedule =
    Schedule(
        groupId = groupId,
        groupName = groupName,
        scheduleId = scheduleId,
        scheduleName = scheduleName,
        location = location.toModel(),
        scheduleTime = LocalDateTime.parse(scheduleTime),
        memberSize = memberSize,
        scheduleStatus = when (scheduleStatus) {
            "RUN" -> ScheduleStatus.RUNNING
            "WAIT" -> ScheduleStatus.WAITING
            "TERM" -> ScheduleStatus.TERMINATED
            else -> ScheduleStatus.BEFORE_JOIN
        }
    )
