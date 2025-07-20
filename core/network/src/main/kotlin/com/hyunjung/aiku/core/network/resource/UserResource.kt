package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/users")
class UserResource {

    @Resource("nickname")
    data class CheckNickname(
        val parent: UserResource = UserResource(),
        val nickname: String
    )
}