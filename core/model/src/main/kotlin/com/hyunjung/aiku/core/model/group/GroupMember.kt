package com.hyunjung.aiku.core.model.group

import com.hyunjung.aiku.core.model.profile.ProfileImage

data class GroupMember(
    override val id: Long,
    override val nickname: String,
    override val profileImage: ProfileImage
) : GroupMemberBase(id, nickname, profileImage)