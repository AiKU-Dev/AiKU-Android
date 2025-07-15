package com.hyunjung.aiku.core.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.navigation.AikuComposeNavigator
import com.hyunjung.aiku.core.navigation.LocalComposeNavigator

@Composable
fun AikuPreviewTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalComposeNavigator provides AikuComposeNavigator()
    ) {
        AiKUTheme(content = content)
    }
}