package com.hyunjung.aiku.core.model.schedule

import java.time.LocalDateTime

data class UpcomingSchedule(
    override val id: Long,
    override val title: String,
    override val location: Location,
    override val scheduleTime: LocalDateTime,
    override val scheduleStatus: ScheduleStatus,
    val groupId: Long,
    val groupName: String,
    val memberSize: Int,
) : ScheduleBase(id, title, location, scheduleTime, scheduleStatus)