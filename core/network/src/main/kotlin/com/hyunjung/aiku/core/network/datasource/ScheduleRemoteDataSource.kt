package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import java.time.LocalDateTime

interface ScheduleRemoteDataSource {
    suspend fun getUpcomingSchedules(
        page: Int = 1,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): List<UpcomingSchedule>

    suspend fun getGroupSchedules(
        groupId: Long,
        page: Int = 1,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): List<GroupSchedule>
}