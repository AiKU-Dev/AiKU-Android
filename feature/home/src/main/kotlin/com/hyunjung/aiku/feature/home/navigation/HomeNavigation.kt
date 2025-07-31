package com.hyunjung.aiku.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.feature.home.HomeScreen

fun NavController.navigateToHomeClearBackStack() = navigateAndClearBackStack(AikuRoute.HomeRoute)

fun NavGraphBuilder.homeScreen(
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    onGroupClick: (Long) -> Unit,
) {
    composable<AikuRoute.HomeRoute> {
        HomeScreen(
            onScheduleClick = onScheduleClick,
            onGroupClick = onGroupClick,
        )
    }
}