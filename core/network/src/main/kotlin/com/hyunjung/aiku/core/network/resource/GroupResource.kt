package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/groups")
class GroupResource(val page: Int? = null) {
    @Resource("{id}")
    class Id(val parent: GroupResource = GroupResource(), val id: Long) {
        @Resource("schedules")
        class Schedules(
            val parent: Id,
            val page: Int? = 1,
            val startDate: String? = null,
            val endDate: String? = null
        )
    }
}