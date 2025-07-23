package com.hyunjung.aiku.core.model.group

import java.time.LocalDateTime

data class GroupSummary(
    val groupId: Long,
    val groupName: String,
    val memberSize: Int,
    val lastScheduleTime: LocalDateTime?
)