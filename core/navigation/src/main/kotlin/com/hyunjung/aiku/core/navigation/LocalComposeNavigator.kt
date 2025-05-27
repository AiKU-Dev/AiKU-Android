package com.hyunjung.aiku.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalComposeNavigator: ProvidableCompositionLocal<AppComposeNavigator<AikuScreen>> =
    compositionLocalOf {
        error(
            "No AppComposeNavigator<AikuScreen> was provided." +
                    "Did you forget to wrap this Composable with CompositionLocalProvider?"
        )
    }

val currentComposeNavigator: AppComposeNavigator<AikuScreen>
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeNavigator.current