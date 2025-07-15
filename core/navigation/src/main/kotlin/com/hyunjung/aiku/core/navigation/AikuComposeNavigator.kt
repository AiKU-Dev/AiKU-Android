package com.hyunjung.aiku.core.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import javax.inject.Inject

class AikuComposeNavigator @Inject constructor() : AppComposeNavigator<AikuRoute>() {

    override fun navigate(route: AikuRoute, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
        val options = optionsBuilder?.let { navOptions(it) }
        navigationCommands.tryEmit(ComposeNavigationCommand.NavigateToRoute(route, options))
    }

    override fun navigateAndClearBackStack(route: AikuRoute) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateToRoute(
                route,
                navOptions {
                    popUpTo(0)
                },
            ),
        )
    }

    override fun navigateToTopLevelDestination(route: AikuRoute) {
        if (!route.isTopLevel()) return

        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateToRoute(
                route,
                navOptions {
                    popUpTo(AikuRoute.HomeRoute) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            )
        )
    }

    override fun popUpTo(route: AikuRoute, inclusive: Boolean) {
        navigationCommands.tryEmit(ComposeNavigationCommand.PopUpToRoute(route, inclusive))
    }
}
