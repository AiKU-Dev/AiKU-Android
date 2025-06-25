package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.datasource.ScheduleDataSource
import com.hyunjung.aiku.core.data.model.GroupSchedule
import com.hyunjung.aiku.core.data.model.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

internal class DefaultScheduleRepository @Inject constructor(
    private val scheduleDataSource: ScheduleDataSource
) : ScheduleRepository {
    override fun getSchedules(
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
    ): Flow<List<Schedule>> = flow {
        emit(scheduleDataSource.getSchedules(page, startDate, endDate))
    }

    override fun getGroupSchedules(
        groupId: Long,
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
    ): Flow<List<GroupSchedule>> = flow {
        emit(scheduleDataSource.getGroupSchedules(groupId, page, startDate, endDate))
    }

}