package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.fake.FakeGroupDataSource
import com.hyunjung.aiku.core.model.GroupDetail
import com.hyunjung.aiku.core.model.GroupOverview
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DefaultGroupRepositoryTest {
    private lateinit var groupDataSource: GroupRemoteDataSource
    private lateinit var subject: DefaultGroupRepository

    @Before
    fun setup() {
        groupDataSource = FakeGroupDataSource()
        subject = DefaultGroupRepository(groupDataSource)
    }


    @Test
    fun `getGroups returns expected list`() = runTest {
        val result: List<GroupOverview> = subject.getGroups(page = 1).first()
        assertEquals(2, result.size)
        assertEquals("전공기초프로젝트", result[0].groupName)
        assertEquals("산학협력프로젝트", result[1].groupName)
    }

    @Test
    fun `getGroup returns correct group detail`() = runTest {
        val result: GroupDetail = subject.getGroupById(id = 1L).first()
        assertEquals(1L, result.groupId)
        assertEquals("산학협력프로젝트", result.groupName)
        assertEquals(2, result.members.size)
        assertEquals("지정희", result.members[0].nickname)
        val resultMemberProfile = result.members[0].memberProfileImage
        if (resultMemberProfile is MemberProfileImage.Avatar) {
            assertEquals(AvatarType.BOY, resultMemberProfile.type)
        }
    }
}
