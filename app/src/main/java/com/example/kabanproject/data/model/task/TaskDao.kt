package com.example.kabanproject.data.model.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Inserir uma nova tarefa ou atualizar se já existir (upsert)
    @Upsert
    suspend fun upsertTask(task: Task)

    // Excluir uma tarefa
    @Delete
    suspend fun deleteTask(task: Task)

    // Obter todas as tarefas (usando Flow para observar as mudanças em tempo real)
    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>

    // Obter uma tarefa específica pelo ID
    @Query("SELECT * FROM Task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    // Obter tarefas com um determinado status
    @Query("SELECT * FROM Task WHERE status = :status")
    fun getTasksByStatus(status: String): Flow<List<Task>>

    // Obter todas as tarefas associadas a um projeto específico
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    fun getTasksByProjectId(projectId: Int): Flow<List<Task>>
}
