package com.example.kabanproject.model

data class Task(
    val id: Int,
    val title: String,
    val description: String = "", // Adiciona a propriedade "description"
    val status: TaskStatus
)
