package com.example.rxjava2.data.entities

data class Tasks(
    private val taskName: String,
    private val priority: Int,
    private val isCompleted: Boolean
)