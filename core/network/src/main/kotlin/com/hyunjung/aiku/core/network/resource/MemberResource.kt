package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/member")
class MemberResource {

    @Resource("schedules")
    class Schedules(
        val parent: MemberResource = MemberResource(),
        val page: Int = 1,
        val startDate: String? = null,
        val endDate: String? = null,
    )
}