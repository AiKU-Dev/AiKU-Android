package com.hyunjung.aiku.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.hyunjung.aiku.AppEntryState
import com.hyunjung.aiku.core.navigation.LocalComposeNavigator
import com.hyunjung.aiku.feature.auth.navigation.navigateToSignInAndClearBackStack
import com.hyunjung.aiku.feature.home.navigation.navigateToHomeClearBackStack
import com.hyunjung.aiku.navigation.AikuNavHost

@Composable
fun AikuApp(
    appState: AikuAppState,
    appEntryState: AppEntryState,
    modifier: Modifier = Modifier
) {
    val composeNavigator = LocalComposeNavigator.current

    LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(appState.navController)
    }

    LaunchedEffect(appEntryState) {
        when (appEntryState) {
            is AppEntryState.Idle, AppEntryState.Syncing, AppEntryState.Idle -> Unit
            is AppEntryState.Authenticated -> appState.navController.navigateToHomeClearBackStack()
            is AppEntryState.Unauthenticated, is AppEntryState.Error ->
                appState.navController.navigateToSignInAndClearBackStack()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        propagateMinConstraints = true
    ) {
        AikuNavHost(appState)
    }
}