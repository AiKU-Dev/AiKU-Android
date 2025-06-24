package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/groups")
class Groups(val page: Int? = 1) {
    @Resource("{id}")
    class Id(val parent: Groups = Groups(), val id: Long) {
        @Resource("schedules")
        class Schedules(
            val parent: Id,
            val page: Int? = 1,
            val startDate: String? = null,
            val endDate: String? = null
        )
    }
}