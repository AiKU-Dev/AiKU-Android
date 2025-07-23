package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor

@Composable
fun ProfileBackgroundColor.toColor(): Color =
    when (this) {
        ProfileBackgroundColor.GREEN -> AiKUTheme.colors.green05
        ProfileBackgroundColor.YELLOW -> AiKUTheme.colors.yellow05
        ProfileBackgroundColor.PURPLE -> AiKUTheme.colors.purple05
        ProfileBackgroundColor.GRAY -> AiKUTheme.colors.gray03
    }