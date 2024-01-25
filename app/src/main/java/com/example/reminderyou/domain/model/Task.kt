package com.example.reminderyou.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Task (
    val id: Long = 0,
    val title: String = "",
    val description: String? = null,
    val deadline: LocalDateTime = LocalDateTime.now(),
    val isChecked: Boolean = false,
    val categoryId: Int? = null,
)