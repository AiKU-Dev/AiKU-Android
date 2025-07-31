package com.hyunjung.aiku.core.ui.preview

import com.hyunjung.aiku.core.model.group.JoinedGroup
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import java.time.LocalDateTime

private val previewLocalDateTime = LocalDateTime.parse("2025-06-30T12:12:12")

object PreviewParameterData {

    val upcomingSchedules = listOf(
        UpcomingSchedule(
            groupId = 1L,
            groupName = "건국대학교",
            id = 6L,
            title = "산협프 회의",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            memberSize = 5,
            scheduleStatus = ScheduleStatus.RUNNING
        ),
        UpcomingSchedule(
            groupId = 1L,
            groupName = "건국대학교",
            id = 8L,
            title = "팀 정기모임",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            memberSize = 5,
            scheduleStatus = ScheduleStatus.TERMINATED
        ),
        UpcomingSchedule(
            groupId = 1L,
            groupName = "건국대학교",
            id = 9L,
            title = "스터디 회의",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            memberSize = 5,
            scheduleStatus = ScheduleStatus.TERMINATED
        )
    )

    val groupSummaries = (1..10).map {
        JoinedGroup(
            id = it.toLong(),
            name = "그룹 $it",
            memberSize = it,
            lastScheduleTime = previewLocalDateTime
        )
    }
}