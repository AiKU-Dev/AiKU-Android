package com.hyunjung.aiku.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun <T : Any> NavController.navigateSingleTop(
    route: T,
    builder: NavOptionsBuilder.() -> Unit = {}
) = navigate(route) {
    launchSingleTop = true
    builder()
}

fun <T : Any> NavController.navigateAndClearBackStack(route: T) =
    navigateSingleTop(route) {
        popUpTo(0) {
            inclusive = true
        }
    }