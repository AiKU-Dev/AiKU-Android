package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.data.model.GroupSchedule
import com.hyunjung.aiku.core.data.model.ScheduleStatus
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class GroupScheduleResponse(
    val scheduleId: Long,
    val scheduleName: String,
    val location: LocationResponse,
    val scheduleTime: String,
    val scheduleStatus: String,
    val memberSize: Int,
    val accept: Boolean,
)

fun GroupScheduleResponse.toModel(): GroupSchedule = GroupSchedule(
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
    },
    accept = accept
)