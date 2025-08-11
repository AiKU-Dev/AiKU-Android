package com.hyunjung.aiku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.AuthRepository
import com.hyunjung.aiku.core.domain.repository.UserDataRepository
import com.hyunjung.aiku.core.result.Result
import com.hyunjung.aiku.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val appEntryState: StateFlow<AppEntryState> = authRepository.isSignedIn
        .flatMapLatest { isSignedIn ->
            if (!isSignedIn) {
                flowOf(AppEntryState.Unauthenticated)
            } else {
                userDataRepository.syncUserData()
                    .asResult()
                    .map { result ->
                        when (result) {
                            is Result.Error -> AppEntryState.Error(
                                result.exception.message ?: "Unknown Error"
                            )

                            is Result.Loading -> AppEntryState.Syncing
                            is Result.Success -> AppEntryState.Authenticated
                        }
                    }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppEntryState.Idle
        )
}

sealed interface AppEntryState {
    data object Idle : AppEntryState
    data object Syncing : AppEntryState
    data object Authenticated : AppEntryState
    data object Unauthenticated : AppEntryState
    data class Error(val message: String) : AppEntryState
}