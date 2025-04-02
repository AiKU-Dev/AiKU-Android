package com.hyunjung.aiku.core.designsystem.component.textfield

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography

private const val PlaceholderAnimationDuration = 83
private const val PlaceholderAnimationDelay = 67
private val MinTextLineHeight = 24.dp
private val LeadingSpacing = 2.dp
private val IndicatorThickness = 1.dp
private val DefaultIndicatorPadding = 4.dp
private val MinSupportingTextLineHeight = 16.dp

private enum class InputPhase {
    Focused,
    UnfocusedEmpty,
    UnfocusedNotEmpty
}

@Composable
internal fun AikuDecorationBox(
    value: String,
    innerTextField: @Composable () -> Unit,
    visualTransformation: VisualTransformation,
    placeholder: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    supporting: @Composable (() -> Unit)? = null,
    shape: Shape = AikuTextFieldDefaults.shape,
    showIndicator: Boolean = true,
    isError: Boolean = false,
    interactionSource: InteractionSource,
    contentPadding: PaddingValues,
    colors: AikuTextFieldColors,
) {
    val transformedText =
        remember(value, visualTransformation) {
            visualTransformation.filter(AnnotatedString(value))
        }
            .text
            .text

    val isFocused = interactionSource.collectIsFocusedAsState().value
    val inputState =
        when {
            isFocused -> InputPhase.Focused
            transformedText.isEmpty() -> InputPhase.UnfocusedEmpty
            else -> InputPhase.UnfocusedNotEmpty
        }

    val transition = updateTransition(inputState, label = "TextFieldInputState")

    val placeholderOpacity =
        transition.animateFloat(
            label = "PlaceholderOpacity",
            transitionSpec = {
                if (InputPhase.Focused isTransitioningTo InputPhase.UnfocusedEmpty) {
                    tween(
                        durationMillis = PlaceholderAnimationDelay,
                        easing = LinearEasing
                    )
                } else if (
                    InputPhase.UnfocusedEmpty isTransitioningTo InputPhase.Focused ||
                    InputPhase.UnfocusedNotEmpty isTransitioningTo InputPhase.UnfocusedEmpty
                ) {
                    tween(
                        durationMillis = PlaceholderAnimationDuration,
                        delayMillis = PlaceholderAnimationDelay,
                        easing = LinearEasing
                    )
                } else {
                    spring()
                }
            }
        ) {
            when (it) {
                InputPhase.Focused -> 1f
                InputPhase.UnfocusedEmpty -> 1f
                InputPhase.UnfocusedNotEmpty -> 0f
            }
        }

    val showPlaceholder by remember {
        derivedStateOf(structuralEqualityPolicy()) { placeholderOpacity.value > 0f }
    }
    val decoratedPlaceholder: @Composable (() -> Unit)? =
        if (placeholder != null && transformedText.isEmpty() && showPlaceholder) {
            @Composable {
                Box(Modifier.graphicsLayer { alpha = placeholderOpacity.value }) {
                    Decoration(
                        contentColor = colors.placeholderColor,
                        textStyle = AikuTypography.Body1,
                        content = placeholder
                    )
                }
            }
        } else null

    val decoratedLeading: @Composable (() -> Unit)? =
        leading?.let {
            @Composable {
                Decoration(
                    contentColor = colors.leadingColor,
                    content = it
                )
            }
        }

    val decoratedTrailing: @Composable (() -> Unit)? =
        trailing?.let {
            @Composable {
                Decoration(
                    contentColor = colors.trailingColor,
                    content = it
                )
            }
        }

    val decoratedSupporting: @Composable (() -> Unit)? =
        supporting?.let {
            @Composable {
                Decoration(
                    contentColor = colors.supportingColor(isError),
                    textStyle = AikuTypography.Caption1,
                    content = it
                )
            }
        }

    val indicator: @Composable (() -> Unit)? =
        if (showIndicator) {
            @Composable {
                Canvas(
                    Modifier
                        .fillMaxWidth()
                        .height(IndicatorThickness)
                ) {
                    drawLine(
                        color = colors.indicatorColor,
                        strokeWidth = IndicatorThickness.toPx(),
                        start = Offset(0f, IndicatorThickness.toPx() / 2),
                        end = Offset(size.width, IndicatorThickness.toPx() / 2),
                    )
                }
            }
        } else null

    AikuTextFieldLayer(
        textField = innerTextField,
        placeholder = decoratedPlaceholder,
        leading = decoratedLeading,
        trailing = decoratedTrailing,
        indicator = indicator,
        supporting = decoratedSupporting,
        shape = shape,
        paddingValues = contentPadding,
        containerColor = colors.containerColor
    )
}

@Composable
private fun Decoration(contentColor: Color, textStyle: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content
    )
}

@Composable
private fun Decoration(contentColor: Color, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        content = content
    )
}

@Composable
private fun AikuTextFieldLayer(
    textField: @Composable () -> Unit,
    placeholder: @Composable (() -> Unit)?,
    leading: @Composable (() -> Unit)?,
    trailing: @Composable (() -> Unit)?,
    supporting: @Composable (() -> Unit)?,
    indicator: @Composable (() -> Unit)?,
    shape: Shape,
    paddingValues: PaddingValues,
    containerColor: Color,
) {
    Column{
        AikuSurface(
            shape = shape,
            color = containerColor,
        ) {
            Row(
                modifier = Modifier.padding(paddingValues)
            ) {
                if (leading != null) {
                    Box(
                        Modifier
                            .heightIn(min = MinTextLineHeight)
                            .wrapContentHeight()
                            .padding(end = LeadingSpacing)
                    ) {
                        leading()
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = MinTextLineHeight)
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center,
                    propagateMinConstraints = true,
                ) {
                    if (placeholder != null) {
                        placeholder()
                    }
                    textField()
                }
                if (trailing != null) {
                    Box(
                        Modifier
                            .heightIn(min = MinTextLineHeight)
                            .wrapContentHeight()
                            .padding(start = LeadingSpacing)
                    ) {
                        trailing()
                    }
                }
            }
        }
        if (indicator != null) {
            Box(
                modifier = Modifier.padding(top = DefaultIndicatorPadding),
            ) {
                indicator()
            }
        }
        if (supporting != null) {
            Box(
                Modifier
                    .heightIn(min = MinSupportingTextLineHeight)
                    .wrapContentHeight()
                    .padding(top = DefaultIndicatorPadding)
            ) {
                supporting()
            }
        }
    }
}