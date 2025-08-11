package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.UserData
import kotlinx.serialization.Serializable

// todo: kakaoId 와 email 부분 api 수정 요청하기

@Serializable
data class UserDataResponse(
    val memberId: Long,
    val nickname: String,
    val kakaoId: Long?,
    val memberProfile: MemberProfileResponse,
    val point: Int,
)

fun UserDataResponse.toModel(): UserData =
    UserData(
        id = memberId,
        point = point,
        email = kakaoId.toString(),
        kakaoId = kakaoId.toString(),
        nickname = nickname,
        profileImage = memberProfile.toModel(),
    )