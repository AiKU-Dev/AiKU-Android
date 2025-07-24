package com.hyunjung.aiku.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuDialog
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuLimitedTextField
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.feature.home.R

@Composable
internal fun CreateGroupDialog(
    onDismiss: () -> Unit,
    onCreateGroup: (String) -> Unit,
) {
    var groupName by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf(GroupNameValidationResult.NONE) }

    AikuDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .background(
                    color = AiKUTheme.colors.white,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AikuText(
                text = stringResource(R.string.feature_home_create_group_dialog_title),
                style = AiKUTheme.typography.body1SemiBold,
            )
            Spacer(Modifier.height(32.dp))
            AikuLimitedTextField(
                value = groupName,
                onValueChange = {
                    groupName = it
                    validationResult = validateGroupName(it)
                },
                placeholder = stringResource(R.string.feature_home_create_group_dialog_placeholder),
                maxLength = 15,
                isError = validationResult != GroupNameValidationResult.NONE,
                supporting = {
                    AikuText(
                        text = stringResource(validationResult.stringResId),
                        style = AiKUTheme.typography.caption1
                    )
                }
            )
            Spacer(Modifier.height(56.dp))
            AikuButton(
                onClick = {
                    val result = validateGroupName(groupName)
                    if (result == GroupNameValidationResult.NONE) {
                        onCreateGroup(groupName)
                    } else {
                        validationResult = result
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = groupName.isNotBlank() && validationResult == GroupNameValidationResult.NONE,
            ) {
                AikuText(text = stringResource(R.string.feature_home_create_group_dialog_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateGroupDialogPreview() {
    AiKUTheme {
        CreateGroupDialog(
            onDismiss = {},
            onCreateGroup = {},
        )
    }
}

private enum class GroupNameValidationResult(val stringResId: Int) {
    NONE(R.string.feature_home_create_group_dialog_supporting_text),
    EMPTY(R.string.feature_home_create_group_dialog_error_blank),
    TOO_SHORT(R.string.feature_home_create_group_dialog_error_too_short),
    INVALID_CHARACTERS(R.string.feature_home_create_group_dialog_error_special_chars)
}

private fun validateGroupName(groupName: String): GroupNameValidationResult {
    return when {
        groupName.isBlank() -> GroupNameValidationResult.EMPTY
        groupName.length < 2 -> GroupNameValidationResult.TOO_SHORT
        !groupName.matches(Regex("^[가-힣a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ ]+$")) -> GroupNameValidationResult.INVALID_CHARACTERS
        else -> GroupNameValidationResult.NONE
    }
}