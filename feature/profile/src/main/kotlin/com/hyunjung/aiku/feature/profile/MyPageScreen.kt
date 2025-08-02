package com.hyunjung.aiku.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .background(
                    AiKUTheme.colors.green02,
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                AikuText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.profile_my_page_title),
                    style = AiKUTheme.typography.subtitle3G
                )
            }

            AikuButton(
                onClick = {},
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .offset(y = 24.dp)
                    .padding(horizontal = 20.dp),
                colors = AikuButtonDefaults.buttonColors(
                    containerColor = AiKUTheme.colors.cobaltBlue,
                    contentColor = AiKUTheme.colors.white
                ),
                contentPadding = PaddingValues(vertical = 14.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                AikuText(
                    text = stringResource(R.string.profile_my_page_shop_button),
                    style = AiKUTheme.typography.subtitle3SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    AiKUTheme {
        MyPageScreen()
    }
}