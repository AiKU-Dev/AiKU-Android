package com.hyunjung.aiku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.navigateSingleTop
import com.hyunjung.aiku.core.ui.component.common.TermsDetailScreen
import com.hyunjung.aiku.feature.auth.navigation.authSection
import com.hyunjung.aiku.feature.auth.navigation.navigateToSignUpSingleTop
import com.hyunjung.aiku.feature.home.navigation.homeScreen
import com.hyunjung.aiku.feature.home.navigation.navigateToHomeClearBackStack
import com.hyunjung.aiku.feature.profile.myPageScreen
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
        splashScreen()

        authSection(
            onSignUpCompleted = navController::navigateToHomeClearBackStack,
            onSignUpRequired = navController::navigateToSignUpSingleTop,
            onTermsClick = { termsType ->
                navController.navigateSingleTop(AikuRoute.TermsDetailRoute(termsType))
            },
        )

        homeScreen(
            // todo : navigate
            onScheduleClick = { groupId, scheduleId -> },
            onGroupClick = { groupId -> },
        )

        myPageScreen(
            // todo : navigate
            onClickNotification = {},
            onClickAccount = {},
            onClickNotificationCheck = {},
            onClickPermissionSetting = {},
            onClickHelp = {},
        )

        composable<AikuRoute.TermsDetailRoute> { backStackEntry ->
            val termsType =
                backStackEntry.savedStateHandle.toRoute<AikuRoute.TermsDetailRoute>().termsType
            TermsDetailScreen(termsType)
        }
    }
}