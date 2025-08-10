package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.ProfileImage

data class GroupMemberWithArrivalTimeDiff(
    override val id: Long,
    override val nickname: String,
    override val profileImage: ProfileImage,
    val arrivalTimeDiffMinutes: Int
) : GroupMemberBase(id, nickname, profileImage)