package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.MemberProfileImage

data class GroupMember(
    val memberId: Long,
    val nickname: String,
    val memberProfileImage: MemberProfileImage
)