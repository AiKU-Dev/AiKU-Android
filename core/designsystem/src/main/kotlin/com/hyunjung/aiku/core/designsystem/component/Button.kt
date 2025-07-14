package com.hyunjung.aiku.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

@Composable
fun AikuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AikuButtonDefaults.DefaultShape,
    colors: ButtonColors = AikuButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    shadowElevation: Dp = 0.dp,
    contentPadding: PaddingValues = AikuButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor

    AikuClickableSurface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = containerColor,
        shadowElevation = shadowElevation,
        border = border,
        interactionSource = interactionSource
    ) {
        ProvideContentColorTextStyle(
            contentColor = contentColor,
            textStyle = AiKUTheme.typography.subtitle3SemiBold
        ) {
            Row(
                Modifier
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
private fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit
) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content
    )
}

@Preview
@Composable
private fun AikuButtonPreview() {
    AiKUTheme {
        AikuButton(
            modifier = Modifier
                .size(width = 320.dp, height = 54.dp),
            onClick = {},
        ) {
            Text(
                text = "내용 입력",
                style = AiKUTheme.typography.subtitle3SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun DisabledAikuButtonPreview() {
    AiKUTheme {
        AikuButton(
            enabled = false,
            modifier = Modifier
                .size(width = 320.dp, height = 54.dp),
            onClick = {},
        ) {
            Text(
                text = "내용 입력",
                style = AiKUTheme.typography.subtitle3SemiBold
            )
        }
    }
}

object AikuButtonDefaults {
    private val ButtonHorizontalPadding = 24.dp
    private val ButtonVerticalPadding = 8.dp

    val DefaultShape: Shape = RoundedCornerShape(5.dp)
    val ContentPadding =
        PaddingValues(
            horizontal = ButtonHorizontalPadding,
            vertical = ButtonVerticalPadding,
        )

    @Composable
    fun buttonColors(
        containerColor: Color = AiKUTheme.colors.green05,
        contentColor: Color = AiKUTheme.colors.white,
        disabledContainerColor: Color = AiKUTheme.colors.gray02,
        disabledContentColor: Color = AiKUTheme.colors.white
    ): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        )
    }
}