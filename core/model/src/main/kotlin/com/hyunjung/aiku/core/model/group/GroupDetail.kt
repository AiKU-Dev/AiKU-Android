package com.hyunjung.aiku.core.model.group

data class GroupDetail(
    val groupId: Long,
    val groupName: String,
    val members: List<GroupMember>
)