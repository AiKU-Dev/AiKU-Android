package com.hyunjung.aiku.core.network.resource

import io.ktor.resources.Resource

@Resource("/login")
class AuthResource {

    @Resource("sign-in")
    class SignIn(val parent: AuthResource = AuthResource()) {
        @Resource("kakao")
        class Kakao(val parent: SignIn = SignIn())

        @Resource("apple")
        class Apple(val parent: SignIn = SignIn())
    }

    @Resource("refresh")
    class Refresh(val parent: AuthResource = AuthResource())
}