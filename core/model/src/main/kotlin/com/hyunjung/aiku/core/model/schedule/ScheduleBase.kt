package com.hyunjung.aiku.core.model.schedule

import java.time.LocalDateTime

abstract class ScheduleBase(
    open val id: Long,
    open val title: String,
    open val location: Location,
    open val scheduleTime: LocalDateTime,
    open val scheduleStatus: ScheduleStatus,
)