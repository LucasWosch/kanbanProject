package com.example.kabanproject.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.data.repos.project.IRepository as IProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val repository: IProjectRepository
) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> get() = _projects

    init {
        viewModelScope.launch {
            repository.getAllProjects().collectLatest { lista ->
                _projects.value = lista
            }
        }
    }

    suspend fun getProjectById(projectId: Int): Project? {
        return repository.getProjectById(projectId)
    }

    fun saveProject(project: Project) {
        viewModelScope.launch {
            repository.upsertProject(project)
        }
    }

    fun getAllProjects() {
        viewModelScope.launch {
            repository.getAllProjects().collectLatest { lista ->
                _projects.value = lista
            }
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            repository.deleteProject(project)
        }
    }

    fun addTaskToProject(task: Task, projectId: String) {
        viewModelScope.launch {
            val project = repository.getProjectById(projectId.toInt())
            project?.let {
                // Adiciona a tarefa à lista de tarefas
                val updatedTasks = project.tasks.toMutableList().apply { add(task) }
                val updatedProject = project.copy(tasks = updatedTasks)

                // Salva o projeto atualizado no Firebase
                repository.upsertProject(updatedProject)
            }
        }
    }
}
