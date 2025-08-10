package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.ProfileImage

data class GroupMemberWithPoint(
    override val id: Long,
    override val nickname: String,
    override val profileImage: ProfileImage,
    val point: Int
) : GroupMemberBase(id, nickname, profileImage)
