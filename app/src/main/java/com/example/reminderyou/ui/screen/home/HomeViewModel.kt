package com.example.reminderyou.ui.screen.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Success())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    fun onTaskItemClicked() {
        _uiState.value = HomeUiState.Success().copy(showTaskDetails = true)
    }

    fun onTaskBottomSheetClosed() {
        _uiState.value = HomeUiState.Success().copy(showTaskDetails = false)
    }
}