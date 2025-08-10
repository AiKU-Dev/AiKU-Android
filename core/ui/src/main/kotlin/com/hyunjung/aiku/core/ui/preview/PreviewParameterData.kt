package com.hyunjung.aiku.core.ui.preview

import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.group.JoinedGroup
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import java.time.LocalDateTime

private val previewLocalDateTime = LocalDateTime.parse("2025-06-30T12:12:12")

object PreviewParameterData {

    val upcomingSchedules = List(4) { index ->
        UpcomingSchedule(
            groupId = 1L,
            groupName = "아이쿠 안드로이드",
            id = (index + 1).toLong(),
            title = "일정 ${index + 1}",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            memberSize = 5,
            scheduleStatus = ScheduleStatus.entries[index]
        )
    }

    val groupSchedules = List(4) { index ->
        GroupSchedule(
            id = (index + 1).toLong(),
            title = "그룹 일정 ${index + 1}",
            location = Location(
                latitude = 37.5407,
                longitude = 127.0795,
                locationName = "공학관"
            ),
            scheduleTime = previewLocalDateTime,
            scheduleStatus = ScheduleStatus.entries[index % ScheduleStatus.entries.size],
            memberSize = 5 + index,
            accept = false
        )
    }


    val JoinedGroups = (1..10).map {
        JoinedGroup(
            id = it.toLong(),
            name = "그룹 $it",
            memberSize = it,
            lastScheduleTime = previewLocalDateTime
        )
    }

    val groupMembers = List(4) { index ->
        GroupMember(
            id = (index + 1).toLong(),
            nickname = "사용자${index + 1}",
            memberProfileImage = MemberProfileImage.Avatar(
                type = AvatarType.entries[index],
                backgroundColor = ProfileBackgroundColor.entries[index]
            )
        )
    }
}