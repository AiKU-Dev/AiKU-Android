package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/member")
class Member {

    @Resource("schedules")
    class Schedules(
        val page: Int = 1,
        val startDate: String? = null,
        val endDate: String? = null,
    )
}