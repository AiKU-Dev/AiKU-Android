package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.schedule.Location
import java.time.LocalDateTime

data class GroupScheduleSummary(
    val id: Long,
    val name: String,
    val location: Location,
    val scheduleTime: LocalDateTime,
    val owner: GroupMember
)