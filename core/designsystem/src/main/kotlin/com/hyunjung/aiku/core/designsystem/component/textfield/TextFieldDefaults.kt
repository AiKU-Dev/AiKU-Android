package com.hyunjung.aiku.core.designsystem.component.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

object AikuTextFieldDefaults {
    val shape = RectangleShape
    val contentPadding = PaddingValues(0.dp)

    @Composable
    fun colors(
        textColor: Color = AiKUTheme.colors.typo,
        containerColor: Color = Color.Unspecified,
        cursorColor: Color = AiKUTheme.colors.typo,
        indicatorColor: Color = AiKUTheme.colors.gray03,
        leadingColor: Color = AiKUTheme.colors.gray03,
        trailingColor: Color = AiKUTheme.colors.gray03,
        placeholderColor: Color = AiKUTheme.colors.gray03,
        supportingColor: Color = AiKUTheme.colors.typo,
        errorSupportingColor: Color = AiKUTheme.colors.hurryRed,
    ): AikuTextFieldColors = AikuTextFieldColors(
        textColor = textColor,
        containerColor = containerColor,
        cursorColor = cursorColor,
        indicatorColor = indicatorColor,
        leadingColor = leadingColor,
        trailingColor = trailingColor,
        placeholderColor = placeholderColor,
        supportingColor = supportingColor,
        errorSupportingColor = errorSupportingColor
    )
}