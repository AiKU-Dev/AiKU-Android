package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.group.GroupMember
import kotlinx.serialization.Serializable

@Serializable
data class GroupMemberResponse(
    val memberId: Long,
    val nickname: String,
    val memberProfile: MemberProfileResponse
)

fun GroupMemberResponse.toModel(): GroupMember =
    GroupMember(
        id = memberId,
        nickname = nickname,
        profileImage = memberProfile.toModel()
    )