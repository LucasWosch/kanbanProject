package com.example.kabanproject.data.model.project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Upsert
import com.example.kabanproject.data.model.project.Project

@Dao
interface ProjectDao {


    // Atualizar um projeto existente
    @Upsert
    suspend fun updateProject(project: Project)

    // Excluir um projeto
    @Delete
    suspend fun deleteProject(project: Project)

    // Obter todos os projetos (usando Flow para observar as mudanças em tempo real)
    @Query("SELECT * FROM Project")
    fun getAllProjects(): Flow<List<Project>>

    // Obter um projeto específico pelo ID
    @Query("SELECT * FROM Project WHERE id = :projectId")
    suspend fun getProjectById(projectId: Int): Project?

    // Obter projetos com um determinado status (exemplo de consulta mais complexa)
    @Query("SELECT * FROM Project WHERE status = :status")
    fun getProjectsByStatus(status: String): Flow<List<Project>>
}
