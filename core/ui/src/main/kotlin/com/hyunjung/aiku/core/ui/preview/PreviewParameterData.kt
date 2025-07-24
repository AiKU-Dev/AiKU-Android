package com.hyunjung.aiku.core.ui.preview

import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.Schedule
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import java.time.LocalDateTime

private val previewLocalDateTime = LocalDateTime.parse("2025-06-30T12:12:12")

object PreviewParameterData {

    val schedules = listOf(
        Schedule(
            groupId = 1L,
            groupName = "건국대학교",
            scheduleId = 6L,
            scheduleName = "산협프 회의",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            memberSize = 5,
            scheduleStatus = ScheduleStatus.RUNNING
        ),
        Schedule(
            groupId = 1L,
            groupName = "건국대학교",
            scheduleId = 8L,
            scheduleName = "팀 정기모임",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            memberSize = 5,
            scheduleStatus = ScheduleStatus.TERMINATED
        ),
        Schedule(
            groupId = 1L,
            groupName = "건국대학교",
            scheduleId = 9L,
            scheduleName = "스터디 회의",
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
        GroupSummary(
            groupId = it.toLong(),
            groupName = "그룹 $it",
            memberSize = it,
            lastScheduleTime = previewLocalDateTime
        )
    }
}