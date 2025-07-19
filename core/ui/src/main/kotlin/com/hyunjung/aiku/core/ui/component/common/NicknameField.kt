package com.hyunjung.aiku.core.ui.component.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuLimitedTextField
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuTextFieldDefaults
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.R

@Composable
fun NicknameField(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    checkNicknameDuplication: () -> Unit,
    modifier: Modifier = Modifier
) {
    AikuLimitedTextField(
        value = nickname,
        onValueChange = onNicknameChange,
        placeholder = stringResource(R.string.nickname_placeholder),
        maxLength = 6,
        supporting = {
            AikuClickableSurface(
                onClick = checkNicknameDuplication,
                color = AiKUTheme.colors.gray01,
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = AiKUTheme.colors.gray02
                ),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                AikuText(
                    text = stringResource(R.string.check_duplicate),
                    style = AiKUTheme.typography.caption1,
                    color = AiKUTheme.colors.gray05,
                    modifier = Modifier.padding(12.dp, vertical = 8.dp)
                )
            }
        },
        colors = AikuTextFieldDefaults.colors(
            indicatorColor = AiKUTheme.colors.gray02
        ),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun NicknameFieldWithCheckButtonPreview() {
    var nickname by remember { mutableStateOf("아이쿠") }

    AiKUTheme {
        NicknameField(
            nickname = nickname,
            onNicknameChange = { nickname = it },
            checkNicknameDuplication = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}