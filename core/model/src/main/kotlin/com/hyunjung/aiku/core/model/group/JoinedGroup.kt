package com.hyunjung.aiku.core.model.group

import java.time.LocalDateTime

data class JoinedGroup(
    override val id: Long,
    override val name: String,
    override val memberSize: Int,
    val lastScheduleTime: LocalDateTime? = null,
) : GroupBase(id, name, memberSize)