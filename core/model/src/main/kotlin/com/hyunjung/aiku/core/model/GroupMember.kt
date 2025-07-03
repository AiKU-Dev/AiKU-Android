package com.hyunjung.aiku.core.model

data class GroupMember(
    val memberId: Long,
    val nickname: String,
    val memberProfile: MemberProfile
)