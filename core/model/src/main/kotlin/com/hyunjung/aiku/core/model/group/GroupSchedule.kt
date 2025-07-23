package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.Location
import com.hyunjung.aiku.core.model.ScheduleStatus
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