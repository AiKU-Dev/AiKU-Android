package com.hyunjung.aiku.feature.auth.signin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import com.hyunjung.aiku.core.model.SocialSignInResult
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

    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun startAuthentication(context: Context, socialType: SocialType) {
        viewModelScope.launch {
            authRepository.connectSocialAccount(context, socialType)
                .asResult()
                .collect { result ->
                    signIn(result, socialType)
                }
        }
    }

    fun consumeUiState() {
        _uiState.value = SignInUiState.Idle
    }

    private suspend fun signIn(
        result: Result<SocialSignInResult>,
        socialType: SocialType
    ) {
        when (result) {
            is Result.Loading -> _uiState.value = SignInUiState.Loading
            is Result.Error -> _uiState.value = SignInUiState.Error(result.exception.message)
            is Result.Success -> {
                val (idToken, email) = result.data
                authRepository.signIn(socialType, idToken)
                    .asResult()
                    .filter { it !is Result.Loading }
                    .collect { isSignedInResult ->
                        handleSignInResult(isSignedInResult, socialType, idToken, email)
                    }
            }
        }
    }

    private fun handleSignInResult(
        isSignedInResult: Result<Boolean>,
        socialType: SocialType,
        idToken: String,
        email: String,
    ) {
        _uiState.value = when (isSignedInResult) {
            is Result.Loading -> SignInUiState.Loading
            is Result.Success -> {
                if (isSignedInResult.data) SignInUiState.Success
                else SignInUiState.NeedsSignUp(
                    socialType = socialType,
                    idToken = idToken,
                    email = email
                )
            }

            is Result.Error -> {
                SignInUiState.Error(isSignedInResult.exception.message)
            }
        }
    }
}

sealed interface SignInUiState {
    data object Idle : SignInUiState
    data object Loading : SignInUiState
    data object Success : SignInUiState
    data class NeedsSignUp(val socialType: SocialType, val idToken: String, val email: String) :
        SignInUiState

    data class Error(val message: String?) : SignInUiState
}