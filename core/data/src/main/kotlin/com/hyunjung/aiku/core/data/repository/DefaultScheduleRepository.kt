package com.hyunjung.aiku.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hyunjung.aiku.core.coroutine.AikuDispatchers.IO
import com.hyunjung.aiku.core.coroutine.Dispatcher
import com.hyunjung.aiku.core.data.paging.OffsetPagingSource
import com.hyunjung.aiku.core.domain.repository.ScheduleRepository
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.network.datasource.ScheduleRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

internal class DefaultScheduleRepository @Inject constructor(
    private val scheduleRemoteDataSource: ScheduleRemoteDataSource,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : ScheduleRepository {

    override fun getSchedulePagingData(
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): Flow<PagingData<UpcomingSchedule>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            OffsetPagingSource { page ->
                scheduleRemoteDataSource.getSchedules(
                    page = page,
                    startDate = startDate,
                    endDate = endDate,
                )
            }
        }
    ).flow.flowOn(ioDispatcher)

    override fun getGroupSchedulePagingData(
        groupId: Long,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): Flow<PagingData<GroupSchedule>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            OffsetPagingSource { page ->
                scheduleRemoteDataSource.getGroupSchedules(
                    groupId = groupId,
                    page = page,
                    startDate = startDate,
                    endDate = endDate,
                )
            }
        }
    ).flow.flowOn(ioDispatcher)

}