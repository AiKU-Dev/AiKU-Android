package com.hyunjung.aiku.core.navigation

import kotlinx.serialization.Serializable

sealed interface AikuScreen {

    @Serializable
    data object SplashRoute : AikuScreen

    @Serializable
    data object LoginRoute : AikuScreen

    @Serializable
    data object SignUpRoute : AikuScreen

    @Serializable
    data object TermsRoute : AikuScreen

    @Serializable
    data object HomeRoute : AikuScreen

    @Serializable
    data object AkuChargingStationRoute : AikuScreen

    @Serializable
    data class GroupDetailRoute(val groupId: Long) : AikuScreen

    @Serializable
    data object MyPageRoute : AikuScreen

    @Serializable
    data object ScheduleRoute : AikuScreen

    @Serializable
    data class ScheduleDetailRoute(val groupId: Long, val scheduleId: Long) : AikuScreen
}

fun AikuScreen.isTopLevel(): Boolean {
    return this::class in setOf(
        AikuScreen.HomeRoute::class,
        AikuScreen.MyPageRoute::class,
        AikuScreen.ScheduleRoute::class
    )
}