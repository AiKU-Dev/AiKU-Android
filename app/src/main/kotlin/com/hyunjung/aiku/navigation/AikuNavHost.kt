package com.hyunjung.aiku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.AuthRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.core.navigation.navigateSingleTop
import com.hyunjung.aiku.core.ui.component.common.TermsDetailScreen
import com.hyunjung.aiku.feature.auth.navigation.authSection
import com.hyunjung.aiku.feature.auth.navigation.navigateToSignUpSingleTop
import com.hyunjung.aiku.feature.home.HomeScreen
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
            onAuthenticated = { navController.navigateAndClearBackStack(AikuRoute.HomeRoute) },
            onAuthenticationRequired = { navController.navigateAndClearBackStack(AuthRoute.SignInRoute) },
        )

        authSection(
            onSignInSuccess = { navController.navigateAndClearBackStack(AikuRoute.HomeRoute) },
            onSignUpCompleted = { navController.navigateAndClearBackStack(AikuRoute.HomeRoute) },
            onSignUpRequired = navController::navigateToSignUpSingleTop,
            onTermsClick = { termsType ->
                navController.navigateSingleTop(AikuRoute.TermsDetailRoute(termsType))
            },
        )

        composable<AikuRoute.HomeRoute> {
            HomeScreen()
        }

        composable<AikuRoute.TermsDetailRoute> { backStackEntry ->
            val termsType =
                backStackEntry.savedStateHandle.toRoute<AikuRoute.TermsDetailRoute>().termsType
            TermsDetailScreen(termsType)
        }
    }
}