package com.hyunjung.aiku.feature.auth.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjung.aiku.core.designsystem.component.snackbar.AikuSnackbarHostState
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import com.hyunjung.aiku.core.model.auth.TermsType
import com.hyunjung.aiku.core.ui.extension.toCompressedFile
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun SignUpScreen(
    onSignUpCompleted: () -> Unit,
    onTermsClick: (TermsType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpFormState by viewModel.signUpFormState.collectAsStateWithLifecycle()
    val signUpProfileUiState by viewModel.signUpProfileUiState.collectAsStateWithLifecycle()
    val signUpStep by viewModel.signUpStep.collectAsStateWithLifecycle()

    val snackbarHostState = remember { AikuSnackbarHostState() }

    val localContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collectLatest {
            snackbarHostState.showSnackbar(it.message)
        }
    }

    AnimatedContent(targetState = signUpStep) { step ->
        when (step) {
            SignUpStep.Terms -> SignUpTermsScreen(
                onTermsAgreed = viewModel::onTermsAgreed,
                onTermsClick = onTermsClick,
            )

            SignUpStep.Profile -> SignUpProfileScreen(
                uiState = signUpProfileUiState,
                signUpFormState = signUpFormState,
                snackbarHostState = snackbarHostState,
                onNicknameChange = viewModel::onNicknameChange,
                onCharacterProfileSelected = viewModel::onProfileChange,
                onAlbumImageSelected = { uri ->
                    coroutineScope.launch {
                        try {
                            val file = uri.toCompressedFile(localContext)
                            viewModel.onProfileChange(UserProfileImage.Photo(file))
                        } catch (_: Exception) {
                            viewModel.onAlbumImageCompressionFailed()
                        }
                    }
                },
                checkNicknameDuplication = viewModel::checkNicknameDuplication,
                onRecommenderNicknameChange = viewModel::onRecommenderNicknameChange,
                submitSignUp = viewModel::submitSignUp,
                modifier = modifier
            )

            SignUpStep.Success -> onSignUpCompleted()
        }
    }
}