package com.hyunjung.aiku.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    userAuthRepository: UserAuthRepository
) : ViewModel() {
    val uiState: StateFlow<SplashUiState> = userAuthRepository.isLoggedIn.map { isLoggedIn ->
        if (isLoggedIn) {
            SplashUiState.NavigateToHome
        } else {
            SplashUiState.NavigateToLogin
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SplashUiState.Loading
    )
}

sealed interface SplashUiState {
    object Loading : SplashUiState
    object NavigateToHome : SplashUiState
    object NavigateToLogin : SplashUiState
}