package com.hyunjung.aiku.feature.auth.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.TermsType
import com.hyunjung.aiku.core.navigation.AuthRoute
import com.hyunjung.aiku.core.result.Result
import com.hyunjung.aiku.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _snackbarMessage = MutableSharedFlow<SignUpSnackbarMessage>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    private val _signUpStep: MutableStateFlow<SignUpStep> =
        MutableStateFlow(SignUpStep.Terms)
    val signUpStep: StateFlow<SignUpStep> = _signUpStep.asStateFlow()

    private val _signUpProfileUiState: MutableStateFlow<SignUpProfileUiState> =
        MutableStateFlow(SignUpProfileUiState.Idle)
    val signUpProfileUiState: StateFlow<SignUpProfileUiState> = _signUpProfileUiState.asStateFlow()

    private val _signUpFormState: MutableStateFlow<SignUpForm> = MutableStateFlow(
        SignUpForm(
            idToken = savedStateHandle.toRoute<AuthRoute.SignUpRoute>().idToken,
            email = savedStateHandle.toRoute<AuthRoute.SignUpRoute>().email,
            socialType = savedStateHandle.toRoute<AuthRoute.SignUpRoute>().socialType,
        )
    )
    val signUpFormState: StateFlow<SignUpForm> = _signUpFormState.asStateFlow()

    fun onTermsAgreed(agreedTerms: List<TermsType>) {
        _signUpFormState.update { it.copy(agreedTerms = agreedTerms) }
        _signUpStep.update { SignUpStep.Profile }
    }

    fun onNicknameChange(nickname: String) {
        _signUpFormState.update { it.copy(nickname = nickname) }
        _signUpFormState.update { it.copy(isNicknameDuplicated = false) }
    }

    fun onProfileChange(userProfileImage: UserProfileImage) {
        _signUpFormState.update { it.copy(userProfileImage = userProfileImage) }
    }

    fun onAlbumImageCompressionFailed() {
        viewModelScope.launch {
            _snackbarMessage.emit(SignUpSnackbarMessage.ImageCompressionFailed)
        }
    }

    fun checkNicknameDuplication() {
        viewModelScope.launch {
            if (!_signUpFormState.value.isNicknameValid) {
                _snackbarMessage.emit(SignUpSnackbarMessage.InvalidNickname)
                return@launch
            }
            val checkNicknameDuplicatedResult =
                authRepository.checkNicknameDuplicated(_signUpFormState.value.nickname)
                    .asResult()
                    .first { it !is Result.Loading }

            if (checkNicknameDuplicatedResult is Result.Success) {
                if (checkNicknameDuplicatedResult.data) {
                    _snackbarMessage.emit(SignUpSnackbarMessage.DuplicateNickname)
                } else {
                    _signUpFormState.update { it.copy(isNicknameDuplicated = true) }
                }
            } else if (checkNicknameDuplicatedResult is Result.Error) {
                _snackbarMessage.emit(SignUpSnackbarMessage.UnknownError)
            }
        }
    }

    fun onRecommenderNicknameChange(recommenderNickname: String) {
        _signUpFormState.update { it.copy(recommenderNickname = recommenderNickname) }
    }

    fun submitSignUp() {
        viewModelScope.launch {
            _signUpProfileUiState.update { SignUpProfileUiState.Loading }
            authRepository.signUp(_signUpFormState.value)
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _signUpProfileUiState.update { SignUpProfileUiState.Loading }
                        }

                        is Result.Error -> {
                            _signUpProfileUiState.update {
                                SignUpProfileUiState.Error(result.exception.message)
                            }
                            _snackbarMessage.emit(SignUpSnackbarMessage.UnknownError)
                        }

                        is Result.Success -> {
                            _snackbarMessage.emit(SignUpSnackbarMessage.SignUpSuccess)
                            signInAfterSignUp()
                        }
                    }
                }
        }
    }

    private suspend fun signInAfterSignUp() {
        authRepository.signIn(
            _signUpFormState.value.socialType,
            _signUpFormState.value.idToken
        )
            .asResult()
            .filter { it !is Result.Loading }
            .collect { isSignedInResult ->
                when (isSignedInResult) {
                    is Result.Loading -> {
                        _signUpProfileUiState.update { SignUpProfileUiState.Loading }
                    }

                    is Result.Error -> {
                        _signUpProfileUiState.update {
                            SignUpProfileUiState.Error(isSignedInResult.exception.message)
                        }
                        _snackbarMessage.emit(SignUpSnackbarMessage.UnknownError)
                    }

                    is Result.Success -> {
                        _signUpProfileUiState.update { SignUpProfileUiState.Idle }
                        if (isSignedInResult.data) {
                            _signUpStep.update { SignUpStep.Success }
                        } else {
                            _snackbarMessage.emit(SignUpSnackbarMessage.UnknownError)
                        }
                    }
                }
            }
    }
}

sealed interface SignUpProfileUiState {
    data object Idle : SignUpProfileUiState
    data object Loading : SignUpProfileUiState
    data class Error(val message: String?) : SignUpProfileUiState
}

sealed interface SignUpStep {
    data object Terms : SignUpStep
    data object Profile : SignUpStep
    data object Success : SignUpStep
}

sealed class SignUpSnackbarMessage(val message: String) {

    data object DuplicateNickname : SignUpSnackbarMessage("이미 있는 닉네임입니다!")

    data object InvalidNickname : SignUpSnackbarMessage(
        "닉네임은 한글/영문만 가능하며, 최대 6자까지 입력할 수 있어요."
    )

    data object UnknownError : SignUpSnackbarMessage("알 수 없는 오류가 발생했습니다")

    data object SignUpSuccess : SignUpSnackbarMessage("회원가입이 완료되었어요!")

    data object ImageCompressionFailed : SignUpSnackbarMessage(
        "이미지를 불러올 수 없어요. 다시 시도해주세요."
    )
}