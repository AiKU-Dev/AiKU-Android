package com.hyunjung.aiku.feature.auth.signin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.UserAuthRepository
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.result.Result
import com.hyunjung.aiku.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(context: Context, socialType: SocialType) {
        viewModelScope.launch {
            userAuthRepository.login(context, socialType)
                .asResult()
                .collect { result ->
                    _uiState.value = when (result) {
                        is Result.Loading -> LoginUiState.Loading
                        is Result.Success -> {
                            if (result.data) {
                                LoginUiState.Success
                            } else {
                                LoginUiState.NeedsSignUp
                            }
                        }
                        is Result.Error -> LoginUiState.Error(result.exception.message)
                    }
                }
        }
    }
}

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data object NeedsSignUp : LoginUiState
    data class Error(val message: String?) : LoginUiState
}