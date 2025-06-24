package com.hyunjung.aiku.core.network.ktor.mock

import com.hyunjung.aiku.core.network.resource.Groups
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.resources.href
import io.ktor.resources.serialization.ResourcesFormat

val groupMockEngine = MockEngine { request ->
    val groupsPath = href(ResourcesFormat(), Groups(page = 1))
    val groupDetailPath = href(ResourcesFormat(), Groups.Id(id = 1L))

    when (request.url.fullPath) {
        groupsPath -> respond(
            content = groupListJson,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

        groupDetailPath -> respond(
            content = groupDetailJson,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

        else -> respondError(HttpStatusCode.NotFound)
    }
}

private const val groupListJson = """
{
  "requestId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "result": {
    "page": 1,
    "data": [
      {
        "groupId": 1,
        "groupName": "전공기초프로젝트",
        "memberSize": 5,
        "lastScheduleTime": "2024-07-30T12:12:12"
      },
      {
        "groupId": 2,
        "groupName": "산학협력프로젝트",
        "memberSize": 2,
        "lastScheduleTime": null
      }
    ]
  }
}
"""

private const val groupDetailJson = """
{
  "requestId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "result": {
    "groupId": 1,
    "groupName": "산학협력프로젝트",
    "members": [
      {
        "memberId": 1,
        "nickname": "지정희",
        "memberProfile": {
          "profileType": "IMG",
          "profileImg": "http://amazon.s3.image.jpg",
          "profileCharacter": null,
          "profileBackground": null
        }
      },
      {
        "memberId": 2,
        "nickname": "박소영",
        "memberProfile": {
          "profileType": "CHAR",
          "profileImg": null,
          "profileCharacter": "C01",
          "profileBackground": "RED"
        }
      }
    ]
  }
}
"""
