package com.hyunjung.aiku.core.network.datasource

import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.network.datasource.mock.userMockEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KtorUserRemoteDataSourceTest {
    private lateinit var subject: UserRemoteDataSource

    @BeforeTest
    fun setup() {
        subject = KtorUserRemoteDataSource(
            HttpClient(userMockEngine) {
                install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
                install(Resources)
            }
        )
    }

    @Test
    fun `getUserData returns expected user`() = runTest {
        val user: UserData = subject.getUserData()

        assertEquals(1L, user.id)
        assertEquals("지정희", user.nickname)
        assertEquals("213123", user.kakaoId)
        assertEquals(0, user.point)
        assertNotNull(user.profileImage)
    }
}
