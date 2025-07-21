package com.hyunjung.aiku.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.feature.splash.SplashScreen

fun NavController.navigateToSplash() =
    navigate(route = AikuRoute.SplashRoute) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }

fun NavGraphBuilder.splashScreen(
    onAuthenticated: () -> Unit,
    onAuthenticationRequired: () -> Unit,
) {
    composable<AikuRoute.SplashRoute> {
        SplashScreen(
            onAuthenticated = onAuthenticated,
            onAuthenticationRequired = onAuthenticationRequired
        )
    }
}