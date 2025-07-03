package com.hyunjung.aiku.core.data.fake

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupMember
import com.hyunjung.aiku.core.model.GroupOverview
import com.hyunjung.aiku.core.model.MemberProfile
import java.time.LocalDateTime

class FakeGroupDataSource : GroupDataSource {

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
                memberProfile = MemberProfile(
                    profileType = "IMG",
                    profileImg = "http://amazon.s3.image.jpg",
                    profileCharacter = null,
                    profileBackground = null
                )
            ),
            GroupMember(
                memberId = 2L,
                nickname = "박소영",
                memberProfile = MemberProfile(
                    profileType = "CHAR",
                    profileImg = null,
                    profileCharacter = "C01",
                    profileBackground = "RED"
                )
            )
        )
    )

    override suspend fun addGroup(name: String): Result<Unit> = Result.success(Unit)
}
