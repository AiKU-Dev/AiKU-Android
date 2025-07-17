package com.hyunjung.aiku.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyunjung.aiku.core.navigation.AuthRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.core.navigation.navigateSingleTop
import com.hyunjung.aiku.core.terms.TermsType
import com.hyunjung.aiku.feature.auth.signin.SignInScreen
import com.hyunjung.aiku.feature.auth.signup.SignUpScreen
import com.hyunjung.aiku.feature.auth.signup.SignUpTermsScreen

fun NavController.navigateToSignInAndClearBackStack() =
    navigateAndClearBackStack(AuthRoute.SignInRoute)

fun NavController.navigateToSignUpTermsAndClearBackStack() =
    navigateAndClearBackStack(AuthRoute.SignUpTermsRoute)

fun NavController.navigateToSignUpSingleTop(agreedTerms: List<TermsType>) =
    navigateSingleTop(AuthRoute.SignUpRoute(agreedTerms))


fun NavGraphBuilder.authSection(
    onLoginSuccess: () -> Unit,
    onSignUpRequired: () -> Unit,
    onNavigateToSignUp: (List<TermsType>) -> Unit,
    onNavigateToTermsDetail: (TermsType) -> Unit,
) {
    navigation<AuthRoute.AuthBaseRoute>(startDestination = AuthRoute.SignInRoute) {
        composable<AuthRoute.SignInRoute> {
            SignInScreen(
                onLoginSuccess = onLoginSuccess,
                onSignUpRequired = onSignUpRequired
            )
        }
        composable<AuthRoute.SignUpTermsRoute> {
            SignUpTermsScreen(
                onAgreeClick = onNavigateToSignUp,
                onTermsClick = onNavigateToTermsDetail,
            )
        }
        composable<AuthRoute.SignUpRoute> {
            SignUpScreen(
                onLoginSuccess = onLoginSuccess,
            )
        }
    }
}