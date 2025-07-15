package com.hyunjung.aiku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.hyunjung.aiku.core.navigation.AikuScreen
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
        startDestination = AikuScreen.SplashRoute
    ) {
        splashScreen(
            onLoginSuccess = { navController.navigateAndClearBackStack(AikuScreen.HomeRoute) },
            onLoginRequired = { navController.navigateAndClearBackStack(AikuScreen.LoginRoute) },
        )
    }
}