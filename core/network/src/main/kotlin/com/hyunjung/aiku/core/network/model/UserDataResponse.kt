package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.UserData
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    val memberId: Long,
    val nickname: String,
    val kakaoId: String?,
    val memberProfile: MemberProfileResponse,
    val point: Int,
)

fun UserDataResponse.toModel(): UserData =
    UserData(
        id = memberId,
        point = point,
        email = kakaoId ?: "",
        kakaoId = kakaoId ?: "",
        nickname = nickname,
        profileImage = memberProfile.toModel(),
    )