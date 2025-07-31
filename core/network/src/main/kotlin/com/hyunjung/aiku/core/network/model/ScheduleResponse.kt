package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
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

fun ScheduleResponse.toModel(): UpcomingSchedule =
    UpcomingSchedule(
        groupId = groupId,
        groupName = groupName,
        id = scheduleId,
        title = scheduleName,
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
