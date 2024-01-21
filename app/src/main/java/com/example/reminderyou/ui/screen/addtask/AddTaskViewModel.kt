package com.example.reminderyou.ui.screen.addtask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.reminderyou.util.dateFormatter
import com.example.reminderyou.util.timeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

data class AddTaskUiState(
    val datePicked: String = "",
    val timePicked: String = ""
)

@HiltViewModel
class AddTaskViewModel @Inject constructor() : ViewModel() {

    var titleState by mutableStateOf("")
        private set

    var descriptionState by mutableStateOf("")
        private set


    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState = _uiState.asStateFlow()


    fun updateDatePicked(date: Long) {
        val datePicked = Instant.ofEpochMilli(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        _uiState.update {
            it.copy(
                datePicked = datePicked.format(dateFormatter)
            )
        }
    }

    fun updateTimePicked(hour: Int, minute: Int) {
        val timePicked = LocalTime.of(hour, minute)
        _uiState.update {
            it.copy(
                timePicked = timePicked.format(timeFormatter)
            )
        }
    }
}

