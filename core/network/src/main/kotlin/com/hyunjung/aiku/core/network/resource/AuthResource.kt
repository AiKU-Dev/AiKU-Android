package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/login/sign-in")
class AuthResource() {
    @Resource("kakao")
    class Kakao(val parent: AuthResource = AuthResource())
}