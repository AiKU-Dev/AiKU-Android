package com.hyunjung.aiku.core.data.model

data class GroupMember(
    val memberId: Long,
    val nickname: String,
    val memberProfile: MemberProfile
)

data class MemberProfile(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)