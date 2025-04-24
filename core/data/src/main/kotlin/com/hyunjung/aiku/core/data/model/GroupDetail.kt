package com.hyunjung.aiku.core.data.model

data class GroupDetail(
    val groupId: Long,
    val groupName: String,
    val members: List<GroupMember>
)