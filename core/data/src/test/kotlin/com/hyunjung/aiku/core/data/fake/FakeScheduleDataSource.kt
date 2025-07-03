package com.hyunjung.aiku.core.data.fake

import com.hyunjung.aiku.core.data.datasource.ScheduleDataSource
import com.hyunjung.aiku.core.model.GroupSchedule
import com.hyunjung.aiku.core.model.Location
import com.hyunjung.aiku.core.model.Schedule
import com.hyunjung.aiku.core.model.ScheduleStatus
import java.time.LocalDateTime

class FakeScheduleDataSource : ScheduleDataSource {

    override suspend fun getSchedules(
        page: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<Schedule> = listOf(
        Schedule(
            groupId = 1L,
            groupName = "건국대학교",
            scheduleId = 6L,
            scheduleName = "운영체제",
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
            scheduleId = 6L,
            scheduleName = "운영체제",
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
