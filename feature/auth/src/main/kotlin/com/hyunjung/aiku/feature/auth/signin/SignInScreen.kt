package com.hyunjung.aiku.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.ui.component.common.LoadingOverlayContainer
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import com.hyunjung.aiku.feature.auth.R
import com.hyunjung.aiku.core.ui.R as UiR

@Composable
internal fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onSignUpRequired: (SocialType, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val localContext = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        when (val result = uiState) {
            is SignInUiState.Success -> onSignInSuccess()
            is SignInUiState.NeedsSignUp -> {
                viewModel.consumeUiState()
                onSignUpRequired(result.socialType, result.idToken, result.email)
            }

            else -> Unit
        }
    }

    SignInScreen(
        onKakaoSignInClick = {
            viewModel.startAuthentication(
                context = localContext,
                socialType = SocialType.KAKAO
            )
        },
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    uiState: SignInUiState = SignInUiState.Idle,
    onKakaoSignInClick: () -> Unit = {}
) {
    LoadingOverlayContainer(
        isLoading = uiState is SignInUiState.Loading,
        indicator = {
            AikuLoadingWheel(
                modifier = Modifier.size(80.dp),
                contentDescription = stringResource(R.string.feature_auth_signin_checking),
            )
        },
        color = AiKUTheme.colors.green05,
        contentAlignment = Alignment.Center,
        modifier = modifier
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
            if (uiState !is SignInUiState.Loading) {
                AikuButton(
                    onClick = onKakaoSignInClick,
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
}

@Preview
@Composable
private fun SignInScreenPreview() {
    AikuPreviewTheme {
        SignInScreen(uiState = SignInUiState.Loading)
    }
}