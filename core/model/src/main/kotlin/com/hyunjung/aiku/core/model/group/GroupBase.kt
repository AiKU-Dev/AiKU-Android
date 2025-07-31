package com.hyunjung.aiku.core.model.group

abstract class GroupBase(
    open val id: Long,
    open val name: String,
    open val memberSize: Int,
)