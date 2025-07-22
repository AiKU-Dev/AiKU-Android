package com.hyunjung.aiku.core.model

import com.hyunjung.aiku.core.model.profile.MemberProfile

data class GroupMember(
    val memberId: Long,
    val nickname: String,
    val memberProfile: MemberProfile
)