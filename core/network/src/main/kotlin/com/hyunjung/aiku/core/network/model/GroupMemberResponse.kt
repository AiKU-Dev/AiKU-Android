package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.GroupMember
import kotlinx.serialization.Serializable

@Serializable
data class GroupMemberResponse(
    val memberId: Long,
    val nickname: String,
    val memberProfile: MemberProfileResponse
)

fun GroupMemberResponse.toModel(): GroupMember =
    GroupMember(
        memberId = memberId,
        nickname = nickname,
        memberProfileImage = memberProfile.toModel()
    )