package com.example.kabanproject.data.model.project

import androidx.room.PrimaryKey
import com.example.kabanproject.data.model.task.Task

data class Project(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String = "",
    var status: String = "",
    var tasks: List<Task> = emptyList()
) {
    // Construtor sem argumentos requerido para o Firebase
    constructor() : this(null, "", "", emptyList())
}
