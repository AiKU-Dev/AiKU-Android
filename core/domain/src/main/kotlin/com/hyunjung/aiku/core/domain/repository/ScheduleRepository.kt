package com.hyunjung.aiku.core.domain.repository

import androidx.paging.PagingData
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ScheduleRepository {
    fun getUpcomingSchedulePagingData(
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): Flow<PagingData<UpcomingSchedule>>

    fun getGroupSchedulePagingData(
        groupId: Long,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): Flow<PagingData<GroupSchedule>>
}