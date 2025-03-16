package com.hyunjung.aiku.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

private val IconMinimumSize: Dp = 20.dp

@Composable
fun AikuIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = IconMinimumSize,
    contentDescription: String? = null,
    padding: PaddingValues = PaddingValues(0.dp),
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
) {
    AikuIconButtonContainer(
        onClick = onClick,
        size = size,
        modifier = modifier,
        padding = padding,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun AikuIconButton(
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = IconMinimumSize,
    padding: PaddingValues = PaddingValues(0.dp),
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
) {
    AikuIconButtonContainer(
        onClick = onClick,
        size = size,
        modifier = modifier,
        padding = padding,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}

@Composable
private fun AikuIconButtonContainer(
    onClick: () -> Unit,
    size: Dp,
    modifier: Modifier,
    padding: PaddingValues,
    enabled: Boolean,
    colors: IconButtonColors,
    interactionSource: MutableInteractionSource?,
    content: @Composable () -> Unit
) {
    AikuClickableSurface(
        modifier = Modifier
            .size(size)
            .padding(padding)
            .then(modifier),
        onClick = onClick,
        color = if (enabled) colors.containerColor else colors.disabledContainerColor,
        interactionSource = interactionSource,
        indication = ripple(radius = size / 2)
    ) {
        val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

@Preview(showBackground = true)
@Composable
private fun AikuIconButtonPreview() {
    AiKUTheme {
        AikuIconButton(
            onClick = {},
            imageVector = Icons.Default.FavoriteBorder,
        )
    }
}