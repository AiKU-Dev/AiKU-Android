package com.hyunjung.aiku.core.network.datasource.mock

import com.hyunjung.aiku.core.network.resource.UserResource
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.resources.href
import io.ktor.resources.serialization.ResourcesFormat

/**
 * UserResource 전용 MockEngine
 *
 * - GET /user (예: UserResource()) → 사용자 단건 조회 응답
 * - 그 외 → 404
 */
val userMockEngine = MockEngine { request ->
    val userPath = href(ResourcesFormat(), UserResource())

    when {
        request.method == HttpMethod.Get && request.url.fullPath == userPath -> respond(
            content = userJson, // 아래 상수의 JSON 반환
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

        else -> respondError(HttpStatusCode.NotFound)
    }
}

/**
 * 서버 응답 예시
 *
 * 주의:
 * - 현재 샘플은 email 필드가 없고, id 대신 memberId를 내려요.
 * - DTO(UserDataResponse)와 mapper에서 필드명을 맞춰 매핑하도록 해 주세요.
 * - profile 관련 키는 서버 스키마에 맞춰 유지했습니다.
 */
private const val userJson = """
{
  "requestId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "result": {
    "memberId": 1,
    "nickname": "지정희",
    "kakaoId": "012341234",
    "memberProfile": {
      "profileType": "IMG",
      "profileImg": "http://amazon.s3.image.jpg",
      "profileCharacter": null,
      "profileBackground": null
    },
    "title": {
      "titleMemberId": 3,
      "titleName": "전장의 지배자",
      "titleDescription": "전장의 지배자",
      "titleImg": "http://www.image.com"
    },
    "point": 0
  }
}
"""
