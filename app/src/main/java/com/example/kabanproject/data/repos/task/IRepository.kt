package com.example.kabanproject.data.repos.task

import com.example.kabanproject.data.model.task.Task
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(taskId: Int): Task?
    fun getTasksByStatus(status: String): Flow<List<Task>>
    fun getTasksByProjectId(projectId: Int): Flow<List<Task>>
}
