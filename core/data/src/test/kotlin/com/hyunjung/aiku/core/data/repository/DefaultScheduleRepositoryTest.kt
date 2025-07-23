package com.hyunjung.aiku.core.data.repository

import com.hyunjung.aiku.core.data.fake.FakeScheduleDataSource
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.schedule.Schedule
import com.hyunjung.aiku.core.network.datasource.ScheduleRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DefaultScheduleRepositoryTest {

    private lateinit var scheduleDataSource: ScheduleRemoteDataSource
    private lateinit var subject: DefaultScheduleRepository

    @Before
    fun setup() {
        scheduleDataSource = FakeScheduleDataSource()
        subject = DefaultScheduleRepository(scheduleDataSource)
    }

    @Test
    fun `getSchedules returns expected schedule list`() = runTest {
        val result: List<Schedule> = subject.getSchedules(page = 1).first()
        assertEquals(1, result.size)
        val schedule = result.first()
        assertEquals(1L, schedule.groupId)
        assertEquals("건국대학교", schedule.groupName)
        assertEquals("운영체제", schedule.scheduleName)
        assertEquals("공학관", schedule.location.locationName)
    }

    @Test
    fun `getGroupSchedules returns expected group schedule list`() = runTest {
        val result: List<GroupSchedule> = subject.getGroupSchedules(groupId = 1L, page = 1).first()
        assertEquals(1, result.size)
        val groupSchedule = result.first()
        assertEquals(6L, groupSchedule.scheduleId)
        assertEquals("운영체제", groupSchedule.scheduleName)
        assertEquals("공학관", groupSchedule.location.locationName)
        assertEquals(true, groupSchedule.accept)
    }
}
