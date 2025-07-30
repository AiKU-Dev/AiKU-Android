package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import java.time.LocalDateTime

data class GroupSchedule(
    val id: Long,
    val scheduleName: String,
    val location: Location,
    val scheduleTime: LocalDateTime,
    val scheduleStatus: ScheduleStatus,
    val memberSize: Int,
    val accept: Boolean
)