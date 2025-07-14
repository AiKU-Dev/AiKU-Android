package com.hyunjung.aiku.feature.home

sealed interface HomeGroupUiState {
    data object Loading : HomeGroupUiState
    data object Idle : HomeGroupUiState
    data class Error(val message: String) : HomeGroupUiState
}