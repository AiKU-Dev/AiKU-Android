package com.hyunjung.aiku.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val aikuDispatcher: AikuDispatchers)

enum class AikuDispatchers {
    Default,
    IO,
}