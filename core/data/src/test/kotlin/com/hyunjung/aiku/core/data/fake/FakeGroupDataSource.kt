package com.hyunjung.aiku.core.data.fake

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import java.time.LocalDateTime

class FakeGroupDataSource : GroupRemoteDataSource {

    override suspend fun getGroups(page: Int): List<GroupSummary> = listOf(
        GroupSummary(
            groupId = 1L,
            groupName = "전공기초프로젝트",
            memberSize = 5,
            lastScheduleTime = LocalDateTime.parse("2024-07-30T12:12:12")
        ),
        GroupSummary(
            groupId = 2L,
            groupName = "산학협력프로젝트",
            memberSize = 2,
            lastScheduleTime = null
        )
    )

    override suspend fun getGroupById(id: Long): GroupDetail = GroupDetail(
        groupId = id,
        groupName = "산학협력프로젝트",
        members = listOf(
            GroupMember(
                memberId = 1L,
                nickname = "지정희",
                memberProfileImage = MemberProfileImage.Photo(url = "http://amazon.s3.image.jpg")
            ),
            GroupMember(
                memberId = 2L,
                nickname = "박소영",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.BOY,
                    backgroundColor = ProfileBackgroundColor.GREEN
                )
            )
        )
    )

    override suspend fun addGroup(name: String): Result<Unit> = Result.success(Unit)
}
