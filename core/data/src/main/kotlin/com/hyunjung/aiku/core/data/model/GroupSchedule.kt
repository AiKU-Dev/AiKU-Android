package com.hyunjung.aiku.core.data.model

import java.time.LocalDateTime

data class GroupSchedule(
    val scheduleId: Long,
    val scheduleName: String,
    val location: Location,
    val scheduleTime: LocalDateTime,
    val scheduleStatus: ScheduleStatus,
    val memberSize: Int,
    val accept: Boolean
)