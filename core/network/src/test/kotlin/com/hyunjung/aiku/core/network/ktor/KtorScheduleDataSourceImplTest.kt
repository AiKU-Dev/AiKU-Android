package com.hyunjung.aiku.core.network.ktor

import com.hyunjung.aiku.core.data.datasource.ScheduleDataSource
import com.hyunjung.aiku.core.data.model.GroupSchedule
import com.hyunjung.aiku.core.data.model.Schedule
import com.hyunjung.aiku.core.network.ktor.mock.scheduleMockEngine
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
    private lateinit var subject: ScheduleDataSource

    @BeforeTest
    fun setup() {
        subject = KtorScheduleDataSourceImpl(HttpClient(scheduleMockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Resources)
        })
    }

    @Test
    fun `getSchedules returns expected list`() = runTest {
        val result: List<Schedule> = subject.getSchedules(page = 1)
        assertEquals(1, result.size)
        assertEquals("운영체제", result[0].scheduleName)
        assertEquals("RUNNING", result[0].scheduleStatus.name)
    }

    @Test
    fun `getGroupSchedules returns expected group schedule list`() = runTest {
        val result: List<GroupSchedule> = subject.getGroupSchedules(groupId = 1L)
        assertEquals(1, result.size)
        assertEquals(6L, result[0].scheduleId)
        assertEquals(true, result[0].accept)
    }
}
