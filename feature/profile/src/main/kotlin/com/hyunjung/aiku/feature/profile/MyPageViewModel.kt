package com.hyunjung.aiku.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.domain.repository.UserDataRepository
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.result.Result
import com.hyunjung.aiku.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
) : ViewModel() {

    val uiState: StateFlow<MyPageUiState> = userDataRepository.userData
        .asResult()
        .map { result ->
            when (result) {
                is Result.Error -> MyPageUiState.Error(result.exception.message)
                is Result.Loading -> MyPageUiState.Loading
                is Result.Success -> MyPageUiState.Success(result.data)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MyPageUiState.Loading
        )
}

sealed interface MyPageUiState {
    data class Error(val message: String?) : MyPageUiState
    data object Loading : MyPageUiState
    data class Success(val userData: UserData) : MyPageUiState
}