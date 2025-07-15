package com.hyunjung.aiku.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.R
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AiKUTheme.colors.green05),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AikuText(
                text = stringResource(R.string.app_title),
                color = AiKUTheme.colors.cobaltBlue,
                style = AiKUTheme.typography.subtitle4G
            )
            AikuText(
                text = stringResource(R.string.app_name),
                color = AiKUTheme.colors.cobaltBlue,
                style = AiKUTheme.typography.headline1G
            )
            Spacer(modifier = Modifier.padding(24.dp))
            SplashCharactersRow()
        }
    }
}

@Composable
private fun SplashCharactersRow() {
    Row(
        modifier = Modifier
            .wrapContentWidth(align = Alignment.CenterHorizontally, unbounded = true),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_char_1),
            contentDescription = "Character 1",
        )
        Image(
            painter = painterResource(id = R.drawable.img_char_2),
            contentDescription = "Character 2",

            )
        Image(
            painter = painterResource(id = R.drawable.img_char_3),
            contentDescription = "Character 3"
        )
        Image(
            painter = painterResource(id = R.drawable.img_char_4),
            contentDescription = "Character 4",
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    AikuPreviewTheme {
        SplashScreen(onSplashFinished = {})
    }
}