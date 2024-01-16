package com.example.reminderyou.data.local.fake

import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

object DataSource {
    val categories = listOf(
        Category(
            id = 1,
            name = "Work",
            tasksQuantity = 2
        ),
        Category(
            id = 2,
            name = "Work",
            tasksQuantity = 4
        ),
        Category(
            id = 3,
            name = "Work",
            tasksQuantity = 5
        ),
        Category(
            id = 4,
            name = "Work",
            tasksQuantity = 20
        )
    )

    val tasks = listOf(
        Task(
            id = 1,
            title = "test 1",
            description = "oelo 1",
            date = LocalDate.now(),
            hour = LocalTime.now(),
            category = categories.first()
        ),
        Task(
            id = 2,
            title = "test 2",
            description = "oelo 2",
            date = LocalDate.now(),
            hour = LocalTime.now(),
            category = categories.first()
        ),
        Task(
            id = 3,
            title = "test 3",
            description = "oelo 3",
            date = LocalDate.now(),
            hour = LocalTime.now(),
            category = categories.first()
        ),
        Task(
            id = 4,
            title = "test 4",
            description = "oelo 4",
            date = LocalDate.now(),
            hour = LocalTime.now(),
            category = categories.first()
        )
    )
}