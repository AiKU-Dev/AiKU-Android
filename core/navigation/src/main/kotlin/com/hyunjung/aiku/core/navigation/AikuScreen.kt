package com.hyunjung.aiku.core.navigation

import kotlinx.serialization.Serializable

sealed interface AikuScreen {

    @Serializable
    data object Login : AikuScreen

    @Serializable
    data object SignUp : AikuScreen

    @Serializable
    data object Terms : AikuScreen

    @Serializable
    data object Home : AikuScreen

    @Serializable
    data object AkuChargingStation : AikuScreen

    @Serializable
    data class GroupDetail(val groupId: Long) : AikuScreen

    @Serializable
    data object MyPage : AikuScreen

    @Serializable
    data class Schedule(val groupId: Long, val scheduleId: Long) : AikuScreen
}
