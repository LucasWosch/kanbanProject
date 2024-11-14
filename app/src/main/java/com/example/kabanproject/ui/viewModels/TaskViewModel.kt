package com.example.kabanproject.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.data.repos.task.IRepository as ITaskRepository
import com.example.kabanproject.data.repos.project.IRepository  as IProjectRepository // Ajuste para o repositório de projetos correto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: ITaskRepository,
    private val projectRepository: IProjectRepository // Repositório de projetos para manipulação de projetos
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks().collectLatest { lista ->
                _tasks.value = lista
            }
        }
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return taskRepository.getTaskById(taskId)
    }

    fun saveTask(task: Task) {
        viewModelScope.launch {
            taskRepository.upsertTask(task)
        }
    }

    fun saveTaskToProject(task: Task, projectId: String) {
        viewModelScope.launch {
            try {
                // Obtém o projeto do repositório de projetos usando o ID
                val project = projectRepository.getProjectById(projectId.toInt())
                if (project != null) {
                    // Cria uma cópia do projeto com a nova tarefa adicionada
                    val updatedProject = project.copy(tasks = project.tasks + task)
                    // Atualiza o projeto com a nova lista de tarefas no Firebase
                    projectRepository.upsertProject(updatedProject)
                }
            } catch (e: Exception) {
                e.printStackTrace() // Trate o erro conforme necessário
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
