package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupMember
import com.hyunjung.aiku.core.data.model.MemberProfile
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

fun GroupDetailResult.toGroupDetail(): GroupDetail =
    GroupDetail(
        groupId = groupId,
        groupName = groupName,
        members = members.map { it.toGroupMember() }
    )


fun GroupMemberResponse.toGroupMember(): GroupMember =
    GroupMember(
        memberId = memberId,
        nickname = nickname,
        memberProfile = memberProfile.toMemberProfile()
    )

fun MemberProfileResponse.toMemberProfile(): MemberProfile =
    MemberProfile(
        profileType = profileType,
        profileImg = profileImg,
        profileCharacter = profileCharacter,
        profileBackground = profileBackground
    )

