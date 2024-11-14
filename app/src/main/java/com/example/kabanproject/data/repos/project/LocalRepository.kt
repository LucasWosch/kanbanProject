package com.example.kabanproject.data.repos.project

import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.model.project.ProjectDao
import com.example.kabanproject.data.repos.project.IRepository
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val projectDao: ProjectDao) : IRepository {

    // Inserir ou atualizar um projeto
    override suspend fun upsertProject(project: Project) {
        projectDao.updateProject(project) // Usa o método de upsert do DAO
    }

    // Excluir um projeto
    override suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    // Obter todos os projetos
    override fun getAllProjects(): Flow<List<Project>> {
        return projectDao.getAllProjects()
    }

    // Obter um projeto específico pelo ID
    override suspend fun getProjectById(projectId: Int): Project? {
        return projectDao.getProjectById(projectId)
    }

    // Obter projetos com um status específico
    override fun getProjectsByStatus(status: String): Flow<List<Project>> {
        return projectDao.getProjectsByStatus(status)
    }
}
