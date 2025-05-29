package com.hyunjung.aiku.core.navigation

import androidx.navigation.NavOptions

sealed interface NavigationCommand {
    data object NavigateUp : NavigationCommand
}

sealed interface ComposeNavigationCommand : NavigationCommand {
    data class NavigateToRoute<T : Any>(val route: T, val options: NavOptions? = null) :
        ComposeNavigationCommand

    data class PopUpToRoute<T : Any>(val route: T, val inclusive: Boolean) :
        ComposeNavigationCommand
}