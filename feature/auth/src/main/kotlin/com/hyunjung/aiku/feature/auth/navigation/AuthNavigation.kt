package com.hyunjung.aiku.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyunjung.aiku.core.model.auth.SocialType
import com.hyunjung.aiku.core.model.auth.TermsType
import com.hyunjung.aiku.core.navigation.AuthRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.core.navigation.navigateSingleTop
import com.hyunjung.aiku.feature.auth.signin.SignInScreen
import com.hyunjung.aiku.feature.auth.signup.SignUpScreen

fun NavController.navigateToSignInAndClearBackStack() =
    navigateAndClearBackStack(AuthRoute.SignInRoute)

fun NavController.navigateToSignUpSingleTop(
    socialType: SocialType,
    idToken: String,
    email: String,
) = navigateSingleTop(
    AuthRoute.SignUpRoute(
        socialType = socialType,
        idToken = idToken,
        email = email,
    )
)


fun NavGraphBuilder.authSection(
    onSignUpCompleted: () -> Unit,
    onSignUpRequired: (SocialType, String, String) -> Unit,
    onTermsClick: (TermsType) -> Unit,
) {
    navigation<AuthRoute.AuthBaseRoute>(startDestination = AuthRoute.SignInRoute) {
        composable<AuthRoute.SignInRoute> {
            SignInScreen(onSignUpRequired = onSignUpRequired)
        }
        composable<AuthRoute.SignUpRoute> {
            SignUpScreen(
                onTermsClick = onTermsClick,
                onSignUpCompleted = onSignUpCompleted,
            )
        }
    }
}