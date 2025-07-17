package com.hyunjung.aiku.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import com.hyunjung.aiku.feature.auth.R
import com.hyunjung.aiku.core.ui.R as UiR

@Composable
fun SignInScreen(
    onLoginSuccess: () -> Unit,
    onSignUpRequired: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val localContext = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> onLoginSuccess()
            is LoginUiState.NeedsSignUp -> onSignUpRequired()
            else -> Unit
        }
    }

    SignInScreen(
        onKakaoLoginClick = {
            viewModel.login(
                context = localContext,
                socialType = SocialType.KAKAO
            )
        },
        modifier = modifier
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    onKakaoLoginClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AiKUTheme.colors.green05),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(UiR.drawable.img_char_upper_body),
                contentDescription = null
            )
            AikuText(
                text = stringResource(UiR.string.app_title),
                style = AiKUTheme.typography.subtitle4G,
                color = AiKUTheme.colors.cobaltBlue,
            )
            AikuText(
                text = stringResource(UiR.string.app_name),
                style = AiKUTheme.typography.headline1G,
                color = AiKUTheme.colors.cobaltBlue,
            )
            Spacer(Modifier.height(40.dp))
            AikuButton(
                onClick = onKakaoLoginClick,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.kakao_login_large_wide),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AikuPreviewTheme {
        SignInScreen()
    }
}