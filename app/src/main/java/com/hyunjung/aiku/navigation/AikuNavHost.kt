package com.hyunjung.aiku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.feature.splash.navigation.splashScreen
import com.hyunjung.aiku.ui.AikuAppState

@Composable
fun AikuNavHost(
    appState: AikuAppState,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = AikuRoute.SplashRoute
    ) {
        splashScreen(
            onLoginSuccess = { navController.navigateAndClearBackStack(AikuRoute.HomeRoute) },
            onLoginRequired = { navController.navigateAndClearBackStack(AikuRoute.LoginRoute) },
        )
    }
}