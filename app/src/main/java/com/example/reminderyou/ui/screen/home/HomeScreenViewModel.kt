package com.example.reminderyou.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

sealed class HomeScreenUiState {
    data class Success(val showTasksDetails: Boolean = false) : HomeScreenUiState()
    object Loading : HomeScreenUiState()
    object Error : HomeScreenUiState()
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Success())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()


}