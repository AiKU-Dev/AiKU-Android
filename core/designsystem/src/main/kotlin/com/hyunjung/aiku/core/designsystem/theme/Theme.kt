package com.hyunjung.aiku.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

private val LocalColors = compositionLocalOf<AikuColors> {
    error("No colors provided!")
}

@Composable
fun AiKUTheme(
    colors: AikuColors = AikuColors.defaultColors(),
    background: AikuBackground = AikuBackground.defaultBackground(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors,
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

val LocalAikuContentColor = compositionLocalOf { Color.Black }

object AiKUTheme {
    val colors: AikuColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}
