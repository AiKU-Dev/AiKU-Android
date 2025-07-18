package com.hyunjung.aiku.core.data.fake

import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupMember
import com.hyunjung.aiku.core.model.GroupOverview
import com.hyunjung.aiku.core.model.MemberProfile
import com.hyunjung.aiku.core.model.ProfileBackground
import com.hyunjung.aiku.core.model.ProfileCharacter
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import java.time.LocalDateTime

class FakeGroupDataSource : GroupRemoteDataSource {

    override suspend fun getGroups(page: Int): List<GroupOverview> = listOf(
        GroupOverview(
            groupId = 1L,
            groupName = "전공기초프로젝트",
            memberSize = 5,
            lastScheduleTime = LocalDateTime.parse("2024-07-30T12:12:12")
        ),
        GroupOverview(
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
                memberProfile = MemberProfile.ImgProfile(profileImg = "http://amazon.s3.image.jpg")
            ),
            GroupMember(
                memberId = 2L,
                nickname = "박소영",
                memberProfile = MemberProfile.CharProfile(
                    profileCharacter = ProfileCharacter.C01,
                    profileBackground = ProfileBackground.GREEN
                )
            )
        )
    )

    override suspend fun addGroup(name: String): Result<Unit> = Result.success(Unit)
}
