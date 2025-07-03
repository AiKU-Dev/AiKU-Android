package com.hyunjung.aiku.core.model

import java.time.LocalDateTime

data class GroupOverview(
    val groupId: Long,
    val groupName: String,
    val memberSize: Int,
    val lastScheduleTime: LocalDateTime?
)