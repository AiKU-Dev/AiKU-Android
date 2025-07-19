package com.hyunjung.aiku.feature.auth.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyunjung.aiku.core.model.MemberProfile
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.navigation.AuthRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _snackbarMessage = MutableSharedFlow<SignUpSnackbarMessage>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    private val _uiState: MutableStateFlow<SignUpUiState> =
        MutableStateFlow(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _signUpFormState: MutableStateFlow<SignUpForm> = MutableStateFlow(
        SignUpForm(agreedTerms = savedStateHandle.toRoute<AuthRoute.SignUpRoute>().agreedTerms)
    )
    val signUpFormState: StateFlow<SignUpForm> = _signUpFormState.asStateFlow()


    fun onNicknameChange(nickname: String) {
        _signUpFormState.update { it.copy(nickname = nickname) }
    }

    fun onProfileChange(memberProfile: MemberProfile) {
        _signUpFormState.update { it.copy(memberProfile = memberProfile) }
    }

    fun checkNicknameDuplication() {
        viewModelScope.launch {
            _snackbarMessage.emit(SignUpSnackbarMessage.DuplicateNickname)
        }
    }

    fun onRecommenderNicknameChange(recommenderNickname: String) {
        _signUpFormState.update { it.copy(recommenderNickname = recommenderNickname) }
    }

    fun submitSignUp(onSuccess: () -> Unit) {

    }
}

sealed interface SignUpUiState {
    data object Idle : SignUpUiState
    data object Loading : SignUpUiState
    data class Error(val message: String) : SignUpUiState
}

sealed class SignUpSnackbarMessage(val message: String) {
    data object DuplicateNickname : SignUpSnackbarMessage("이미 있는 닉네임입니다!")
    data object UnknownError : SignUpSnackbarMessage("알 수 없는 오류가 발생했습니다")
}