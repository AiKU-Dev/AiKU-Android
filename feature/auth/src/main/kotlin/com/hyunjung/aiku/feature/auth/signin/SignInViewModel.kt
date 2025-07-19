package com.hyunjung.aiku.feature.auth.signin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import com.hyunjung.aiku.core.model.SocialLoginResult
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.result.Result
import com.hyunjung.aiku.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(context: Context, socialType: SocialType) {
        viewModelScope.launch {
            authRepository.connectSocialAccount(context, socialType)
                .asResult()
                .collect { result ->
                    handleConnectResult(result, socialType)
                }
        }
    }

    fun consumeUiState() {
        _uiState.value = LoginUiState.Idle
    }

    private suspend fun handleConnectResult(
        result: Result<SocialLoginResult>,
        socialType: SocialType
    ) {
        when (result) {
            is Result.Loading -> _uiState.value = LoginUiState.Loading
            is Result.Error -> _uiState.value = LoginUiState.Error(result.exception.message)
            is Result.Success -> {
                val (idToken, email) = result.data
                authRepository.login(socialType, idToken)
                    .asResult()
                    .filter { it !is Result.Loading }
                    .collect { loginResult ->
                        handleLoginResult(loginResult, socialType, idToken, email)
                    }
            }
        }
    }

    private fun handleLoginResult(
        result: Result<Boolean>,
        socialType: SocialType,
        idToken: String,
        email: String,
    ) {
        _uiState.value = when (result) {
            is Result.Loading -> LoginUiState.Loading
            is Result.Success -> {
                if (result.data) LoginUiState.Success
                else LoginUiState.NeedsSignUp(
                    socialType = socialType,
                    idToken = idToken,
                    email = email
                )
            }

            is Result.Error -> {
                Log.d("testaaa", "${result.exception.message}")
                LoginUiState.Error(result.exception.message)
            }
        }
    }
}

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data class NeedsSignUp(val socialType: SocialType, val idToken: String, val email: String) :
        LoginUiState

    data class Error(val message: String?) : LoginUiState
}