package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.domain.repository.ScheduleRepository
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.Schedule
import com.hyunjung.aiku.core.network.datasource.ScheduleRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

internal class DefaultScheduleRepository @Inject constructor(
    private val scheduleRemoteDataSource: ScheduleRemoteDataSource
) : ScheduleRepository {
    override fun getSchedules(
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
    ): Flow<List<Schedule>> = flow {
        emit(scheduleRemoteDataSource.getSchedules(page, startDate, endDate))
    }

    override fun getGroupSchedules(
        groupId: Long,
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
    ): Flow<List<GroupSchedule>> = flow {
        emit(scheduleRemoteDataSource.getGroupSchedules(groupId, page, startDate, endDate))
    }

}