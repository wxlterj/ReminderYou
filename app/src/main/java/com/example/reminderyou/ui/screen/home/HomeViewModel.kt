package com.example.reminderyou.ui.screen.home

import androidx.lifecycle.ViewModel
import com.example.reminderyou.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    fun checkTask(task: Task, isChecked: Boolean) {
        _uiState.update { currentState ->
            val taskCheckedUpdated = (currentState as HomeUiState.Success).tasksChecked.map {
                if (it == task) {
                    it.copy(isChecked = isChecked)
                } else {
                    it
                }
            }

            currentState.copy(tasksChecked = taskCheckedUpdated)
        }
    }
}