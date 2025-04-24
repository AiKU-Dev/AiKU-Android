package com.hyunjung.aiku.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupDetailResponse(
    val requestId: String,
    val result: GroupDetailResult
)

@Serializable
data class GroupDetailResult(
    val groupId: Long,
    val groupName: String,
    val members: List<GroupMemberResponse>
)

@Serializable
data class GroupMemberResponse(
    val memberId: Long,
    val nickname: String,
    val memberProfile: MemberProfileResponse
)

@Serializable
data class MemberProfileResponse(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)
