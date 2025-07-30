package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.MemberProfileImage

abstract class GroupMemberBase(
    open val id: Long,
    open val nickname: String,
    open val memberProfileImage: MemberProfileImage
)