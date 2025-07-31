package com.hyunjung.aiku.core.data.fake

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.model.group.JoinedGroup
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import java.time.LocalDateTime

class FakeGroupDataSource : GroupRemoteDataSource {

    override suspend fun getJoinedGroups(page: Int): List<JoinedGroup> = listOf(
        JoinedGroup(
            id = 1L,
            name = "전공기초프로젝트",
            memberSize = 5,
            lastScheduleTime = LocalDateTime.parse("2024-07-30T12:12:12")
        ),
        JoinedGroup(
            id = 2L,
            name = "산학협력프로젝트",
            memberSize = 2,
            lastScheduleTime = null
        )
    )

    override suspend fun getGroupDetail(id: Long): GroupDetail = GroupDetail(
        id = id,
        name = "산학협력프로젝트",
        members = listOf(
            GroupMember(
                id = 1L,
                nickname = "지정희",
                memberProfileImage = MemberProfileImage.Photo(url = "http://amazon.s3.image.jpg")
            ),
            GroupMember(
                id = 2L,
                nickname = "박소영",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.BOY,
                    backgroundColor = ProfileBackgroundColor.GREEN
                )
            )
        )
    )

    override suspend fun createGroup(name: String): Result<Unit> = Result.success(Unit)
}
