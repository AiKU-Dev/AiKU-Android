package com.hyunjung.aiku.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

private val LocalAikuColors = compositionLocalOf<AikuColors> {
    error("No colors provided!")
}

private val LocalAikuTypography = compositionLocalOf<AikuTypography> {
    error("No AikuTypography provided!")
}

@Composable
fun AiKUTheme(
    colors: AikuColors = AikuColors.defaultColors(),
    typography: AikuTypography = AikuTypography.defaultTypography(),
    background: AikuBackground = AikuBackground.defaultBackground(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAikuColors provides colors,
        LocalAikuTypography provides typography,
        LocalBackgroundTheme provides background,
    ) {
        Box(
            modifier = Modifier
                .background(background.color)
                .semantics { testTagsAsResourceId = true },
        ) {
            content()
        }
    }
}

object AiKUTheme {
    val colors: AikuColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAikuColors.current

    val typography: AikuTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAikuTypography.current
}
