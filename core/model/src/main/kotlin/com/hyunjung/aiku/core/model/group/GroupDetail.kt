package com.hyunjung.aiku.core.model.group

data class GroupDetail(
    override val id: Long,
    override val name: String,
    val members: List<GroupMember>,
) : GroupBase(id, name, members.size)