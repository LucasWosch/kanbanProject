package com.example.kabanproject.data.repos.task

import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.data.model.task.TaskDao
import com.example.kabanproject.data.repos.task.IRepository
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val taskDao: TaskDao) : IRepository {

    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    override fun getTasksByStatus(status: String): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status)
    }

    override fun getTasksByProjectId(projectId: Int): Flow<List<Task>> {
        return taskDao.getTasksByProjectId(projectId)
    }
}
