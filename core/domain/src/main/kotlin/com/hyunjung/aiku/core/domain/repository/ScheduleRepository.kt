package com.hyunjung.aiku.core.domain.repository

import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.Schedule
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ScheduleRepository {
    fun getSchedules(
        page: Int = 1,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): Flow<List<Schedule>>

    fun getGroupSchedules(
        groupId: Long,
        page: Int = 1,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): Flow<List<GroupSchedule>>
}