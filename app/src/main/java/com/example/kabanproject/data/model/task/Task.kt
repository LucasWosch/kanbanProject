package com.example.kabanproject.data.model.task

import androidx.room.PrimaryKey
import com.example.kabanproject.data.model.TaskStatus

data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String = "",
    val description: String = "", // Adiciona a propriedade "description"
    val status: TaskStatus = TaskStatus.TODO
) {
    // Construtor sem argumentos necessário para o Firebase
    constructor() : this(0, "", "", TaskStatus.TODO)
}
