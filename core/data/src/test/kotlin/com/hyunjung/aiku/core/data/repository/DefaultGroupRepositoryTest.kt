package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.data.demo.DemoGroupDataSource
import com.hyunjung.aiku.core.data.demo.JvmUnitTestDemoAssetManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DefaultGroupRepositoryTest {
    private lateinit var groupDataSource: GroupDataSource
    private lateinit var subject: DefaultGroupRepository
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setup() {
        groupDataSource = DemoGroupDataSource(
            ioDispatcher = UnconfinedTestDispatcher(),
            networkJson = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestDemoAssetManager,
        )
        subject = DefaultGroupRepository(groupDataSource)
    }


    @Test
    fun `getGroups returns non-empty list with correct group name`() {
        testScope.runTest {
            val groups = subject.getGroups(page = 1).first()
            assert(groups.isNotEmpty())
            assertEquals("전공기초프로젝트", groups.first().groupName)
        }
    }

    @Test
    fun `getGroup returns group detail with correct id and member profile`() {
        testScope.runTest {
            val group = subject.getGroup(id = 1L).first()
            assertEquals(1L, group.groupId)
            assertEquals("전공기초프로젝트", group.groupName)
            assert(group.members.isNotEmpty())
            assertNotNull(group.members.first().memberProfile)
        }
    }
}
