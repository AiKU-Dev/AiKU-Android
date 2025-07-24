package com.hyunjung.aiku.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.navigateAndClearBackStack
import com.hyunjung.aiku.feature.home.HomeScreen

fun NavController.navigateToHomeClearBackStack() = navigateAndClearBackStack(AikuRoute.HomeRoute)

fun NavGraphBuilder.homeSection(
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    onGroupSummaryClick: (Long) -> Unit,
) {
    navigation<AikuRoute.HomeRoute>(startDestination = AikuRoute.HomeRoute) {
        composable<AikuRoute.HomeRoute> {
            HomeScreen(
                onScheduleClick = onScheduleClick,
                onGroupSummaryClick = onGroupSummaryClick,
            )
        }
    }
}