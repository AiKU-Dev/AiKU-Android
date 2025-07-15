package com.hyunjung.aiku.core.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import javax.inject.Inject

class AikuComposeNavigator @Inject constructor() : AppComposeNavigator<AikuScreen>() {

    override fun navigate(route: AikuScreen, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
        val options = optionsBuilder?.let { navOptions(it) }
        navigationCommands.tryEmit(ComposeNavigationCommand.NavigateToRoute(route, options))
    }

    override fun navigateAndClearBackStack(route: AikuScreen) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateToRoute(
                route,
                navOptions {
                    popUpTo(0)
                },
            ),
        )
    }

    override fun navigateToTopLevelDestination(route: AikuScreen) {
        if (!route.isTopLevel()) return

        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateToRoute(
                route,
                navOptions {
                    popUpTo(AikuScreen.HomeRoute) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            )
        )
    }

    override fun popUpTo(route: AikuScreen, inclusive: Boolean) {
        navigationCommands.tryEmit(ComposeNavigationCommand.PopUpToRoute(route, inclusive))
    }
}
