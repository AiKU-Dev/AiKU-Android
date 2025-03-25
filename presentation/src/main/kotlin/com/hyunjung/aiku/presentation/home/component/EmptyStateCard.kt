package com.hyunjung.aiku.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R

@Composable
fun EmptyStateCard(
    title: String,
    buttonText: String,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = AikuTypography.Subtitle3_G,
            color = AikuColors.Typo
        )
        Image(
            painter = painterResource(R.drawable.presentation_char_running_boy),
            contentDescription = stringResource(R.string.presentation_char_running_boy_description),
            modifier = Modifier.padding(16.dp)
        )
        AikuButton(
            onClick = onClickButton,
            modifier = Modifier.defaultMinSize(
                minWidth = 112.dp,
                minHeight = 38.dp
            ),
            colors = AikuButtonDefaults.buttonColors(
                containerColor = AikuColors.Gray01,
            ),
            shape = RoundedCornerShape(30.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            border = BorderStroke(
                color = AikuColors.CobaltBlue,
                width = 2.dp
            )
        ) {
            Text(
                text = buttonText,
                style = AikuTypography.Body1_SemiBold,
                color = AikuColors.CobaltBlue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStateCardPreview() {
    AiKUTheme {
        EmptyStateCard(
            title = stringResource(R.string.presentation_home_no_group),
            buttonText = stringResource(R.string.presentation_home_no_group_button),
            onClickButton = {},
        )
    }
}