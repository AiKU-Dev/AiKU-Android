package com.hyunjung.aiku.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuLimitedTextField
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R

private enum class GroupNameValidationError(val stringResId: Int) {
    NONE(R.string.presentation_create_group_dialog_supporting_text),
    EMPTY(R.string.presentation_create_group_dialog_error_blank),
    TOO_SHORT(R.string.presentation_create_group_dialog_error_too_short),
    INVALID_CHARACTERS(R.string.presentation_create_group_dialog_error_special_chars)
}

@Composable
fun CreateGroupDialog(
    onDismiss: () -> Unit,
    onCreateGroup: (String) -> Unit,
) {
    var groupName by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf(GroupNameValidationError.NONE) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AikuColors.Gray06.copy(alpha = 0.5f))
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = AikuColors.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.presentation_create_group_dialog_title),
                    style = AikuTypography.Body1_SemiBold,
                )
                Spacer(Modifier.height(32.dp))
                AikuLimitedTextField(
                    value = groupName,
                    onValueChange = {
                        groupName = it
                        errorState = validateGroupName(it)
                    },
                    placeholder = stringResource(R.string.presentation_create_group_dialog_placeholder),
                    maxLength = 15,
                    isError = errorState != GroupNameValidationError.NONE,
                    supporting = {
                        Text(text = stringResource(errorState.stringResId))
                    }
                )
                Spacer(Modifier.height(56.dp))
                AikuButton(
                    onClick = {
                        onCreateGroup(groupName)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = groupName.isNotBlank() && errorState == GroupNameValidationError.NONE,
                ) {
                    Text(
                        text = stringResource(R.string.presentation_create_group_dialog_button),
                        style = AikuTypography.Body1_SemiBold
                    )
                }
            }
        }
    }
}

private fun validateGroupName(groupName: String): GroupNameValidationError {
    return when {
        groupName.isBlank() -> GroupNameValidationError.EMPTY
        groupName.length < 2 -> GroupNameValidationError.TOO_SHORT
        !groupName.matches(Regex("^[가-힣a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ ]+$")) -> GroupNameValidationError.INVALID_CHARACTERS
        else -> GroupNameValidationError.NONE
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