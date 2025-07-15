package com.hyunjung.aiku.core.navigation

import kotlinx.serialization.Serializable

sealed interface AikuRoute {

    @Serializable
    data object SplashRoute : AikuRoute

    @Serializable
    data object LoginRoute : AikuRoute

    @Serializable
    data object SignUpRoute : AikuRoute

    @Serializable
    data object TermsRoute : AikuRoute

    @Serializable
    data object HomeRoute : AikuRoute

    @Serializable
    data object AkuChargingStationRoute : AikuRoute

    @Serializable
    data class GroupDetailRoute(val groupId: Long) : AikuRoute

    @Serializable
    data object MyPageRoute : AikuRoute

    @Serializable
    data object ScheduleRoute : AikuRoute

    @Serializable
    data class ScheduleDetailRoute(val groupId: Long, val scheduleId: Long) : AikuRoute
}

fun AikuRoute.isTopLevel(): Boolean {
    return this::class in setOf(
        AikuRoute.HomeRoute::class,
        AikuRoute.MyPageRoute::class,
        AikuRoute.ScheduleRoute::class
    )
}