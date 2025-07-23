package com.hyunjung.aiku.feature.auth.signup

import android.net.Uri
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.component.snackbar.AikuSnackbarHost
import com.hyunjung.aiku.core.designsystem.component.snackbar.AikuSnackbarHostState
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuLimitedTextField
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuTextField
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuTextFieldDefaults
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import com.hyunjung.aiku.core.model.auth.SignUpForm
import com.hyunjung.aiku.core.model.auth.SocialType
import com.hyunjung.aiku.core.ui.component.common.LoadingOverlayContainer
import com.hyunjung.aiku.core.ui.component.common.NicknameField
import com.hyunjung.aiku.core.ui.component.common.ProfileImagePicker
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import com.hyunjung.aiku.feature.auth.R

@Composable
internal fun SignUpProfileScreen(
    signUpFormState: SignUpForm,
    modifier: Modifier = Modifier,
    uiState: SignUpProfileUiState = SignUpProfileUiState.Idle,
    snackbarHostState: AikuSnackbarHostState = AikuSnackbarHostState(),
    onNicknameChange: (String) -> Unit = {},
    onCharacterProfileSelected: (UserProfileImage.Avatar) -> Unit = {},
    onAlbumImageSelected: (Uri) -> Unit = {},
    checkNicknameDuplication: () -> Unit = {},
    onRecommenderNicknameChange: (String) -> Unit = {},
    submitSignUp: () -> Unit = {},
) {

    var showProfileSelector by remember { mutableStateOf(false) }
    var isOptionMenuVisible by remember { mutableStateOf(false) }

    LoadingOverlayContainer(
        isLoading = uiState is SignUpProfileUiState.Loading,
        indicator = {
            AikuLoadingWheel(
                modifier = Modifier.size(80.dp),
                contentDescription = stringResource(R.string.feature_auth_signup_checking),
            )
        },
        color = AiKUTheme.colors.white,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(showProfileSelector) {
                    if (showProfileSelector) {
                        detectTapGestures(onTap = {
                            showProfileSelector = false
                        })
                    }
                }
                .padding(horizontal = 20.dp, vertical = 40.dp)
        ) {
            Spacer(Modifier.height(20.dp))
            AikuText(
                text = stringResource(R.string.feature_auth_signup_header),
                style = AiKUTheme.typography.headline3G,
            )

            Spacer(Modifier.height(32.dp))
            ProfileImagePicker(
                userProfileImage = signUpFormState.userProfileImage,
                isOptionMenuVisible = isOptionMenuVisible,
                onProfileImageOptionMenuDisMiss = { isOptionMenuVisible = false },
                onEditClick = { isOptionMenuVisible = true },
                onCharacterProfileSelected = onCharacterProfileSelected,
                onAlbumImageSelected = onAlbumImageSelected,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Column(
                modifier = Modifier.padding(vertical = 44.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                NicknameField(
                    nickname = signUpFormState.nickname,
                    onNicknameChange = onNicknameChange,
                    checkNicknameDuplication = checkNicknameDuplication,
                )

                AikuTextField(
                    value = signUpFormState.email,
                    onValueChange = {},
                    singleLine = true,
                    colors = AikuTextFieldDefaults.colors(
                        indicatorColor = AiKUTheme.colors.gray02
                    )
                )

                AikuLimitedTextField(
                    value = signUpFormState.recommenderNickname,
                    onValueChange = onRecommenderNicknameChange,
                    maxLength = 6,
                    placeholder = stringResource(R.string.feature_auth_signup_signup_recommend_nickname),
                    colors = AikuTextFieldDefaults.colors(
                        indicatorColor = AiKUTheme.colors.gray02
                    )
                )
            }


            Spacer(Modifier.weight(1f))

            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AikuSnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            AikuButton(
                onClick = submitSignUp,
                enabled = signUpFormState.isSignUpEnabled,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                AikuText(
                    text = stringResource(R.string.feature_auth_signup_button),
                    style = AiKUTheme.typography.subtitle3SemiBold,
                    color = AiKUTheme.colors.white
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    AikuPreviewTheme {
        SignUpProfileScreen(
            SignUpForm(
                idToken = "",
                socialType = SocialType.KAKAO,
                email = "example@example.com"
            )
        )
    }
}