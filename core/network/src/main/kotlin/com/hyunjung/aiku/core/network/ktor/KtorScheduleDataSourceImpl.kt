package com.hyunjung.aiku.core.network.ktor

import com.hyunjung.aiku.core.data.datasource.ScheduleDataSource
import com.hyunjung.aiku.core.model.GroupSchedule
import com.hyunjung.aiku.core.model.Schedule
import com.hyunjung.aiku.core.network.model.ApiResponse
import com.hyunjung.aiku.core.network.model.GroupSchedulesResultResponse
import com.hyunjung.aiku.core.network.model.SchedulesResultResponse
import com.hyunjung.aiku.core.network.model.toModel
import com.hyunjung.aiku.core.network.resource.Groups
import com.hyunjung.aiku.core.network.resource.Member
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import java.time.LocalDateTime
import javax.inject.Inject

class KtorScheduleDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : ScheduleDataSource {
    override suspend fun getSchedules(
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<Schedule> =
        client.get(Member.Schedules(page, startDate?.toString(), endDate?.toString()))
            .body<ApiResponse<SchedulesResultResponse>>()
            .result.data.map { it.toModel() }

    override suspend fun getGroupSchedules(
        groupId: Long,
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<GroupSchedule> =
        client.get(
            Groups.Id.Schedules(
                parent = Groups.Id(id = groupId),
                page = page,
                startDate = startDate?.toString(),
                endDate = endDate?.toString()
            )
        )
            .body<ApiResponse<GroupSchedulesResultResponse>>()
            .result.data.map { it.toModel() }

}