package com.hyunjung.aiku.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.navigateSingleTop

fun NavController.navigateToMyPageSingleTop() = navigateSingleTop(AikuRoute.MyPageRoute)

fun NavGraphBuilder.myPageScreen(
    onClickNotification: () -> Unit,
    onClickAccount: () -> Unit,
    onClickNotificationCheck: () -> Unit,
    onClickPermissionSetting: () -> Unit,
    onClickHelp: () -> Unit,
) {
    composable<AikuRoute.MyPageRoute> {
        MyPageScreen(
            onClickNotification = onClickNotification,
            onClickAccount = onClickAccount,
            onClickNotificationCheck = onClickNotificationCheck,
            onClickPermissionSetting = onClickPermissionSetting,
            onClickHelp = onClickHelp,
        )
    }
}
