package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.network.datasource.mock.groupMockEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorGroupDataSourceImplTest {
    private lateinit var subject: GroupRemoteDataSource

    @BeforeTest
    fun setup() {
        subject = KtorGroupRemoteDataSource(HttpClient(groupMockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Resources)
        })
    }

    @Test
    fun `getGroups returns expected list`() = runTest {
        val result: List<GroupSummary> = subject.getGroups(page = 1)
        assertEquals(2, result.size)
        assertEquals("전공기초프로젝트", result[0].groupName)
        assertEquals("산학협력프로젝트", result[1].groupName)
    }

    @Test
    fun `getGroup returns correct group detail`() = runTest {
        val result: GroupDetail = subject.getGroupById(id = 1L)
        assertEquals(1L, result.groupId)
        assertEquals("산학협력프로젝트", result.groupName)
        assertEquals(2, result.members.size)
        assertEquals("지정희", result.members[0].nickname)
        val resultMemberProfile = result.members[0].memberProfileImage
        if (resultMemberProfile is MemberProfileImage.Avatar) {
            assertEquals(AvatarType.BOY, resultMemberProfile.type)
        }
    }

    @Test
    fun `addGroup returns success`() = runTest {
        val result = subject.addGroup("새로운 그룹")
        assertEquals(true, result.isSuccess)
    }
}