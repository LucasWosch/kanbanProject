package com.example.kabanproject.data.model.user

import androidx.room.PrimaryKey

data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val username: String = "",
    val email: String = "",
    val password: String = "", // Novo campo para senha
    val createdAt: Long = System.currentTimeMillis()
)

