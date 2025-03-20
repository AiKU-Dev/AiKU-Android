package com.hyunjung.aiku.core.designsystem.component.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.theme.AikuColors

object AikuTextFieldDefaults {
    val shape = RectangleShape
    val contentPadding = PaddingValues(0.dp)

    fun colors(
        textColor: Color = AikuColors.Typo,
        containerColor: Color = Color.Unspecified,
        cursorColor: Color = AikuColors.Typo,
        indicatorColor: Color = AikuColors.Gray03,
        leadingColor: Color = AikuColors.Gray03,
        trailingColor: Color = AikuColors.Gray03,
        placeholderColor: Color = AikuColors.Gray03,
        supportingColor: Color = AikuColors.Typo,
        errorSupportingColor: Color = AikuColors.HurryRed,
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