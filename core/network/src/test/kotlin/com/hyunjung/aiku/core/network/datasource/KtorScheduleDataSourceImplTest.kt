package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.network.datasource.mock.scheduleMockEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorScheduleDataSourceImplTest {
    private lateinit var subject: ScheduleRemoteDataSource

    @BeforeTest
    fun setup() {
        subject = KtorScheduleRemoteDataSource(HttpClient(scheduleMockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Resources)
        })
    }

    @Test
    fun `getSchedules returns expected list`() = runTest {
        val result: List<UpcomingSchedule> = subject.getUpcomingSchedules(page = 1)
        assertEquals(1, result.size)
        assertEquals("운영체제", result[0].title)
        assertEquals("RUNNING", result[0].scheduleStatus.name)
    }

    @Test
    fun `getGroupSchedules returns expected group schedule list`() = runTest {
        val result: List<GroupSchedule> = subject.getGroupSchedules(groupId = 1L)
        assertEquals(1, result.size)
        assertEquals(6L, result[0].id)
        assertEquals(true, result[0].accept)
    }
}
