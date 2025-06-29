package com.hyunjung.aiku.presentation.home.model

sealed interface HomeGroupUiState {
    data object Loading : HomeGroupUiState
    data object Idle : HomeGroupUiState
    data class Error(val message: String) : HomeGroupUiState
}