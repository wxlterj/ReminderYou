package com.example.reminderyou.ui.screen.home

sealed class HomeUiState {
    data class Success(
        val showTaskDetails: Boolean = false
    ) : HomeUiState()
    object Loading : HomeUiState()
    object Error : HomeUiState()
}