package com.hyunjung.aiku.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@NonRestartableComposable
fun AikuSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = Color.Unspecified,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier =
            modifier
                .surface(
                    shape = shape,
                    backgroundColor = color,
                    border = border,
                    shadowElevation = with(LocalDensity.current) { shadowElevation.toPx() }
                ),
        propagateMinConstraints = true,
    ) {
        content()
    }
}

@Composable
@NonRestartableComposable
fun AikuClickableSurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = Color.Unspecified,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication = ripple(),
    content: @Composable () -> Unit
) {
    AikuSurface(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = enabled,
            onClick = onClick
        ),
        shape = shape,
        color = color,
        shadowElevation = shadowElevation,
        border = border,
        content = content
    )
}

@Composable
@NonRestartableComposable
fun AikuSelectableSurface(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = Color.Unspecified,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication = ripple(),
    content: @Composable () -> Unit
) {
    AikuSurface(
        modifier = modifier.selectable(
            selected = selected,
            interactionSource = interactionSource,
            indication = indication,
            enabled = enabled,
            onClick = onClick
        ),
        shape = shape,
        color = color,
        shadowElevation = shadowElevation,
        border = border,
        content = content
    )
}

@Composable
@NonRestartableComposable
fun AikuCheckableSurface(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = Color.Unspecified,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication = ripple(),
    content: @Composable () -> Unit
) {
    AikuSurface(
        modifier = modifier.toggleable(
            value = checked,
            interactionSource = interactionSource,
            indication = indication,
            enabled = enabled,
            onValueChange = onCheckedChange
        ),
        shape = shape,
        color = color,
        shadowElevation = shadowElevation,
        border = border,
        content = content
    )
}

@Stable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Float,
) =
    this
        .then(
            if (shadowElevation > 0f) {
                Modifier.graphicsLayer(
                    shadowElevation = shadowElevation,
                    shape = shape,
                    clip = false
                )
            } else {
                Modifier
            }
        )
        .then(if (border != null) Modifier.border(border, shape) else Modifier)
        .background(color = backgroundColor, shape = shape)
        .clip(shape)

@Preview(showBackground = true, name = "Default AikuSurface")
@Composable
fun AikuSurfacePreview() {
    AikuSurface {
        AikuText(text = "Default Surface")
    }
}

@Preview(showBackground = true, name = "Clickable AikuSurface")
@Composable
fun ClickableAikuSurfacePreview() {
    AikuClickableSurface(onClick = {}) {
        AikuText(text = "Clickable Surface")
    }
}

@Preview(showBackground = true, name = "Selectable AikuSurface")
@Composable
fun SelectableAikuSurfacePreview() {
    var selected by remember { mutableStateOf(false) }

    AikuSelectableSurface(
        selected = selected,
        onClick = { selected = !selected }
    ) {
        AikuText(text = if (selected) "Selected" else "Not Selected")
    }
}

@Preview(showBackground = true, name = "Toggleable AikuSurface")
@Composable
fun ToggleableAikuSurfacePreview() {
    var checked by remember { mutableStateOf(false) }

    AikuCheckableSurface(
        checked = checked,
        onCheckedChange = { checked = it }
    ) {
        AikuText(text = if (checked) "Checked" else "Unchecked")
    }
}


