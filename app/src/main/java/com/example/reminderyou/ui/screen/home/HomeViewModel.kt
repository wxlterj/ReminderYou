package com.example.reminderyou.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.domain.usecase.GetCategoriesUseCase
import com.example.reminderyou.domain.usecase.GetTasksWithCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksWithCategoryUseCase: GetTasksWithCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        loadSuccessState()
    }

    fun loadSuccessState() {
        viewModelScope.launch {
            val tasks = getTasksWithCategoryUseCase()
            val categories = getCategoriesUseCase().first()

            if (_uiState.value is HomeUiState.Loading) {
                _uiState.value = HomeUiState.Success(
                    tasks = tasks,
                    categories = categories
                )
            } else {
                _uiState.update { currentState ->
                    (currentState as HomeUiState.Success).copy(
                        tasks = tasks,
                        categories = categories
                    )
                }
            }
        }
    }


    fun onTaskItemClicked() {
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showTaskDetails = true
            )
        }
    }

    fun onTaskBottomSheetClosed() {
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showTaskDetails = false
            )
        }
    }

    fun checkTask(task: TaskWithCategory, isChecked: Boolean) {
        _uiState.update { currentState ->
            val taskListUpdated =
                (currentState as HomeUiState.Success).tasks.map { taskWithCategoryState ->
                    if (taskWithCategoryState == task) {
                        taskWithCategoryState.copy(
                            task = taskWithCategoryState.task.copy(isChecked = isChecked)
                        )
                    } else {
                        taskWithCategoryState
                    }
                }
            currentState.copy(tasks = taskListUpdated)
        }
    }
    fun deleteTask(task: TaskWithCategory) {
        _uiState.update { currentState ->
            val tasksListMutable = (currentState as HomeUiState.Success).tasks.toMutableList()
            tasksListMutable.remove(task)
            currentState.copy(
                tasks = tasksListMutable.toList()
            )
        }
    }
}

