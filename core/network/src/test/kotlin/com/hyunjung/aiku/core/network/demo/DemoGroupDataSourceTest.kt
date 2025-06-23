package com.hyunjung.aiku.core.network.demo

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupOverview
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DemoGroupDataSourceTest {

    private lateinit var subject: GroupDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        subject = DemoGroupDataSource(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestDemoAssetManager,
        )
    }

    @Test
    fun `getGroups returns non-empty list`() = runTest(testDispatcher) {
        val groups: List<GroupOverview> = subject.getGroups(1)
        assert(groups.isNotEmpty())
        assertEquals("전공기초프로젝트", groups.first().groupName)
    }

    @Test
    fun `getGroup returns correct group detail`() = runTest(testDispatcher) {
        val group: GroupDetail = subject.getGroupById(1)
        assertEquals(1L, group.groupId)
        assertEquals("전공기초프로젝트", group.groupName)
        assert(group.members.isNotEmpty())
        assertNotNull(group.members.first().memberProfile)
    }
}