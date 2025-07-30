package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.MemberProfileImage

data class GroupMemberWithArrivalTimeDiff(
    override val id: Long,
    override val nickname: String,
    override val memberProfileImage: MemberProfileImage,
    val arrivalTimeDiffMinutes: Int
) : GroupMemberBase(id, nickname, memberProfileImage)