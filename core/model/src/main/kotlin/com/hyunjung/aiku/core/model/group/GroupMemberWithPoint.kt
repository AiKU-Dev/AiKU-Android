package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.MemberProfileImage

data class GroupMemberWithPoint(
    override val id: Long,
    override val nickname: String,
    override val memberProfileImage: MemberProfileImage,
    val point: Int
) : GroupMemberBase(id, nickname, memberProfileImage)
