package com.example.reminderyou.domain.model

import com.example.reminderyou.data.local.entity.TaskEntity

data class Category(
    val id: Int = 0,
    val name: String = "",
    val tasks: List<Task>? = null
)