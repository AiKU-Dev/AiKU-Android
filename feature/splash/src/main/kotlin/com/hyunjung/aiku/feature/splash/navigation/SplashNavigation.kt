package com.hyunjung.aiku.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyunjung.aiku.core.navigation.AikuScreen
import com.hyunjung.aiku.feature.splash.SplashScreen

fun NavController.navigateToSplash() =
    navigate(route = AikuScreen.SplashRoute) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }

fun NavGraphBuilder.splashScreen(
    onLoginSuccess: () -> Unit,
    onLoginRequired: () -> Unit,
) {
    composable<AikuScreen.SplashRoute> {
        SplashScreen(
            onLoginSuccess = onLoginSuccess,
            onLoginRequired = onLoginRequired
        )
    }
}