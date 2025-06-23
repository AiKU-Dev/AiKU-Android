package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/groups")
class Groups(val page: Int? = null) {
    @Resource("{id}")
    class Id(val parent: Groups = Groups(), val id: Long)
}