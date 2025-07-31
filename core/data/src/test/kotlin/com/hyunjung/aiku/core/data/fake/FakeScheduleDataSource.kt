package com.hyunjung.aiku.core.data.fake

import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.network.datasource.ScheduleRemoteDataSource
import java.time.LocalDateTime

class FakeScheduleDataSource : ScheduleRemoteDataSource {

    override suspend fun getUpcomingSchedules(
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<UpcomingSchedule> = listOf(
        UpcomingSchedule(
            groupId = 1L,
            groupName = "건국대학교",
            id = 6L,
            title = "운영체제",
            location = Location(
                latitude = 127.1,
                longitude = 111.1,
                locationName = "공학관"
            ),
            scheduleTime = LocalDateTime.parse("2024-08-20T12:12:12"),
            memberSize = 10,
            scheduleStatus = ScheduleStatus.RUNNING
        )
    )

    override suspend fun getGroupSchedules(
        groupId: Long,
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<GroupSchedule> = listOf(
        GroupSchedule(
            id = 6L,
            title = "운영체제",
            location = Location(
                latitude = 127.1,
                longitude = 111.1,
                locationName = "공학관"
            ),
            scheduleTime = LocalDateTime.parse("2024-08-20T12:12:12"),
            scheduleStatus = ScheduleStatus.RUNNING,
            memberSize = 10,
            accept = true
        )
    )
}
