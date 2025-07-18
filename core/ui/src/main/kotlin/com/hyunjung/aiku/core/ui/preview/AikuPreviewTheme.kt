package com.hyunjung.aiku.core.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavOptionsBuilder
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.AppComposeNavigator
import com.hyunjung.aiku.core.navigation.LocalComposeNavigator

private val PreviewNavigator = object : AppComposeNavigator<AikuRoute>() {
    override fun navigate(route: AikuRoute, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) = Unit
    override fun popUpTo(route: AikuRoute, inclusive: Boolean) = Unit
    override fun navigateAndClearBackStack(route: AikuRoute) = Unit
    override fun navigateToTopLevelDestination(route: AikuRoute) = Unit
}

@Composable
fun AikuPreviewTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalComposeNavigator provides PreviewNavigator
    ) {
        AiKUTheme(content = content)
    }
}