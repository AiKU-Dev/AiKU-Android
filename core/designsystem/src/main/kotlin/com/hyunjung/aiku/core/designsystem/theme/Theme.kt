package com.hyunjung.aiku.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AiKUTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}