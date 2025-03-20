package com.hyunjung.aiku.core.designsystem.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.R
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography

@Composable
fun AikuLimitedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = AikuTypography.Body1,
    placeholder: String = "",
    supporting: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showIndicator: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = AikuTextFieldDefaults.contentPadding,
    shape: Shape = AikuTextFieldDefaults.shape,
    colors: AikuTextFieldColors = AikuTextFieldDefaults.colors()
) {
    AikuTextField(
        value = value,
        onValueChange = { newText ->
            val updatedText = if (newText.length <= maxLength) {
                newText
            } else {
                value.take(maxLength - 1) + newText.last()
            }
            onValueChange(updatedText)
        },
        placeholder = { Text(text = placeholder) },
        trailing = {
            if (value.isNotBlank()) {
                Text(
                    text = "${value.length}/$maxLength",
                    style = AikuTypography.Caption1,
                    color = colors.trailingColor
                )
            }
        },
        modifier = modifier,
        textStyle = textStyle,
        contentPadding = contentPadding,
        singleLine = true,
        enabled = enabled,
        isError = isError,
        colors = colors,
        shape = shape,
        supporting = supporting,
        showIndicator = showIndicator,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation
    )
}

@Composable
fun AikuTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = AikuTypography.Body1,
    placeholder: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    supporting: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    showIndicator: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = AikuTextFieldDefaults.contentPadding,
    shape: Shape = AikuTextFieldDefaults.shape,
    colors: AikuTextFieldColors = AikuTextFieldDefaults.colors()
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val mergedTextStyle = textStyle.merge(TextStyle(color = colors.textColor))
    val errorMessage = stringResource(R.string.core_designsystem_text_field_error_default)

    BasicTextField(
        value = value,
        modifier = if (isError) {
            modifier.semantics { error(errorMessage) }
        } else {
            modifier
        },
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox =
            @Composable { innerTextField ->
                AikuDecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    contentPadding = contentPadding,
                    placeholder = placeholder,
                    leading = leading,
                    trailing = trailing,
                    supporting = supporting,
                    shape = shape,
                    showIndicator = showIndicator,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors
                )
            }
    )
}

@Preview(showBackground = true, name = "Empty Limited TextField")
@Composable
private fun AikuEmptyLimitedTextFieldPreview() {
    AiKUTheme {
        AikuLimitedTextField(
            value = "",
            placeholder = "그룹 이름을 입력하세요",
            onValueChange = {},
            maxLength = 5,
            contentPadding = PaddingValues(20.dp)
        )
    }
}

@Preview(showBackground = true, name = "Limited TextField")
@Composable
private fun AikuLimitedTextFieldPreview() {
    AiKUTheme {
        AikuLimitedTextField(
            value = "Aiku 그룹",
            placeholder = "그룹 이름을 입력하세요",
            onValueChange = {},
            maxLength = 10,
            contentPadding = PaddingValues(20.dp)
        )
    }
}

@Preview(showBackground = true, name = "Default TextField")
@Composable
private fun AikuDefaultTextFiledPreview() {
    AiKUTheme {
        AikuTextField(
            value = "Aiku 그룹",
            contentPadding = PaddingValues(20.dp),
            onValueChange = {},
            supporting = {
                Text(
                    text = "supportingText",
                    style = AikuTypography.Caption1
                )
            },
            placeholder = {
                Text(text = "placeholder")
            },
            singleLine = true,
        )
    }
}

@Preview(showBackground = true, name = "Error TextField")
@Composable
private fun AikuErrorDefaultTextFiledPreview() {
    AiKUTheme {
        AikuTextField(
            value = "Aiku 그룹!@#",
            contentPadding = PaddingValues(20.dp),
            onValueChange = {},
            isError = true,
            supporting = {
                Text(
                    text = "특수문자는 입력 불가합니다",
                    style = AikuTypography.Caption1
                )
            },
            placeholder = { Text(text = "placeholder") },
            singleLine = true,
        )
    }
}