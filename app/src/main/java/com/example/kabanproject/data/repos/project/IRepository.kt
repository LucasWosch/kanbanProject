package com.example.kabanproject.data.repos.project

import com.example.kabanproject.data.model.project.Project
import kotlinx.coroutines.flow.Flow

interface IRepository {

    // Inserir ou atualizar um projeto
    suspend fun upsertProject(project: Project)

    // Excluir um projeto
    suspend fun deleteProject(project: Project)

    // Obter todos os projetos
    fun getAllProjects(): Flow<List<Project>>

    // Obter um projeto específico pelo ID
    suspend fun getProjectById(projectId: Int): Project?

    // Obter projetos com um status específico
    fun getProjectsByStatus(status: String): Flow<List<Project>>
}