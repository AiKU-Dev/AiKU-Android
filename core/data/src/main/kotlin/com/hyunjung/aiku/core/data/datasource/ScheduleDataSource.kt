package com.hyunjung.aiku.core.data.datasource

import com.hyunjung.aiku.core.model.GroupSchedule
import com.hyunjung.aiku.core.model.Schedule
import java.time.LocalDateTime

interface ScheduleDataSource {
    suspend fun getSchedules(
        page: Int = 1,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): List<Schedule>

    suspend fun getGroupSchedules(
        groupId: Long,
        page: Int = 1,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): List<GroupSchedule>
}