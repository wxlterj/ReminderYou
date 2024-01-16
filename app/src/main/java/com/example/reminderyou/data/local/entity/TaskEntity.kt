package com.example.reminderyou.data.local.entity

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.reminderyou.domain.model.Category

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val taskId: Long,
    val title: String,
    val description: String,
    val date: Long,
    val time: Long,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
    @ColumnInfo(name = "category_id")
    val categoryId: Int
)