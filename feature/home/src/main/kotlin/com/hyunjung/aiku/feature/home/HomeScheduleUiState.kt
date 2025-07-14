package com.hyunjung.aiku.feature.home

sealed interface HomeScheduleUiState {
    data object Loading : HomeScheduleUiState
    data object Idle : HomeScheduleUiState
    data class Error(val message: String) : HomeScheduleUiState
}