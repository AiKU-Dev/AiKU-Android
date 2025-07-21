package com.hyunjung.aiku.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {
    val uiState: StateFlow<SplashUiState> = authRepository.isSignedIn.map { isSignedIn ->
        if (isSignedIn) {
            SplashUiState.Authenticated
        } else {
            SplashUiState.Unauthenticated
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SplashUiState.Loading
    )
}

sealed interface SplashUiState {
    object Loading : SplashUiState
    object Authenticated : SplashUiState
    object Unauthenticated : SplashUiState
}