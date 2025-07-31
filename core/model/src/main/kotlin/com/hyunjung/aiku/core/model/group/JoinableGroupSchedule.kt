package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.ScheduleBase
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import java.time.LocalDateTime

data class JoinableGroupSchedule(
    override val id: Long,
    override val title: String,
    override val location: Location,
    override val scheduleTime: LocalDateTime,
    val owner: GroupMember
) : ScheduleBase(id, title, location, scheduleTime, ScheduleStatus.BEFORE_JOIN)