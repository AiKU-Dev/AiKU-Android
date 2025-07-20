package com.hyunjung.aiku.core.network.datasource.mock

import com.hyunjung.aiku.core.network.resource.GroupResource
import com.hyunjung.aiku.core.network.resource.MemberResource
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.resources.href
import io.ktor.resources.serialization.ResourcesFormat

val scheduleMockEngine = MockEngine { request ->
    val memberResourceSchedulePath = href(ResourcesFormat(), MemberResource.Schedules())
    val groupSchedulePath = href(ResourcesFormat(), GroupResource.Id.Schedules(parent = GroupResource.Id(id = 1L)))

    when (request.url.fullPath) {
        memberResourceSchedulePath -> respond(
            content = memberScheduleJson,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

        groupSchedulePath -> respond(
            content = groupScheduleJson,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

        else -> respondError(HttpStatusCode.NotFound)
    }
}

private const val memberScheduleJson = """
{
  "requestId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "result": {
    "page": 1,
    "runSchedule": 0,
    "waitSchedule": 3,
    "data": [
      {
        "groupId": 1,
        "groupName": "건국대학교",
        "scheduleId": 6,
        "scheduleName": "운영체제",
        "location": {
          "latitude": 127.1,
          "longitude": 111.1,
          "locationName": "공학관"
        },
        "scheduleTime": "2024-08-20T12:12:12",
        "memberSize": 10,
        "scheduleStatus": "RUN"
      }
    ]
  }
}
"""

private const val groupScheduleJson = """
{
  "requestId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "result": {
    "page": 1,
    "groupId": 1,
    "runSchedule": 0,
    "waitSchedule": 3,
    "data": [
      {
        "scheduleId": 6,
        "scheduleName": "운영체제",
        "location": {
          "latitude": 127.1,
          "longitude": 111.1,
          "locationName": "공학관"
        },
        "scheduleTime": "2024-08-20T12:12:12",
        "scheduleStatus": "RUN",
        "memberSize": 10,
        "accept": true
      }
    ]
  }
}
"""
