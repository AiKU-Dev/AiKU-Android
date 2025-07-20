package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.ProfileBackground

@Composable
fun ProfileBackground.toColor(): Color =
    when (this) {
        ProfileBackground.GREEN -> AiKUTheme.colors.green05
        ProfileBackground.YELLOW -> AiKUTheme.colors.yellow05
        ProfileBackground.PURPLE -> AiKUTheme.colors.purple05
        ProfileBackground.GRAY -> AiKUTheme.colors.gray03
    }