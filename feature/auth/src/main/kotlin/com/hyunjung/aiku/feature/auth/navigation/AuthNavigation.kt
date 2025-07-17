package com.hyunjung.aiku.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyunjung.aiku.core.navigation.AuthRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.feature.auth.signin.SignInScreen

fun NavController.navigateToSignIn() = navigateAndClearBackStack(AuthRoute.SignInRoute)

fun NavGraphBuilder.authSection(
    onLoginSuccess: () -> Unit,
    onSignUpRequired: () -> Unit,
) {
    navigation<AuthRoute.AuthBaseRoute>(startDestination = AuthRoute.SignInRoute) {
        composable<AuthRoute.SignInRoute> {
            SignInScreen(
                onLoginSuccess = onLoginSuccess,
                onSignUpRequired = onSignUpRequired
            )
        }
    }
}