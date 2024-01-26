package com.example.reminderyou.domain.model

import java.time.LocalDateTime

data class Task(
    val id: Long = 0,
    val title: String = "",
    val description: String? = null,
    val deadline: LocalDateTime = LocalDateTime.now(),
    val isChecked: Boolean = false,
    val categoryId: Int? = null,
)