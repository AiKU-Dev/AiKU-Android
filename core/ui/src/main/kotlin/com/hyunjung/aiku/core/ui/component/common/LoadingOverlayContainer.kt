package com.hyunjung.aiku.core.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

@Composable
fun LoadingOverlayContainer(
    isLoading: Boolean,
    indicator: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = color),
        contentAlignment = contentAlignment,
    ) {
        content()
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = AiKUTheme.colors.gray06.copy(alpha = 0.5f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                indicator()
            }
        }
    }
}