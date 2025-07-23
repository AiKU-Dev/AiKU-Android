package com.hyunjung.aiku.core.model.schedule

import java.time.LocalDateTime

data class Schedule(
    val groupId: Long,
    val groupName: String,
    val scheduleId: Long,
    val scheduleName: String,
    val location: Location,
    val scheduleTime: LocalDateTime,
    val memberSize: Int,
    val scheduleStatus: ScheduleStatus,
)