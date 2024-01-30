package com.example.reminderyou.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

fun LocalDateTime.toLong(): Long {
    return this.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
}

fun longToLocalDateTime(long: Long): LocalDateTime {
    val instant = Instant.ofEpochMilli(long)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}