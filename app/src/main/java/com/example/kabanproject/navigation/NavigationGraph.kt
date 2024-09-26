package com.example.kabanproject.navigation

import AddProjectScreen
import Project
import ProjectDetailScreen
import ProjectListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kabanproject.model.Task
import com.example.kabanproject.model.TaskStatus
import com.example.kabanproject.ui.loginScreen.LoginScreen
import com.example.kabanproject.ui.registerScreen.RegisterScreen
import com.example.kabanproject.ui.task.TaskDetailScreen
import com.example.kabanproject.ui.task.TaskFormScreen


@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Tela de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("projectList") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // Tela de Cadastro de Usuários
        composable("register") {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }


        // Tela de Adição de Projetos
        composable("addProject") {
            AddProjectScreen(
                navController = navController,  // Passando o navController aqui
                onAddProjectSuccess = {
                    navController.navigate("projectList") {
                        popUpTo("projectList") { inclusive = true }
                    }
                }
            )
        }
        composable("projectList") {
            ProjectListScreen(
                projects = sampleProjects(),
                onProjectClick = { project ->
                    navController.navigate("projectDetailScreen/${project.id}")
                },
                onAddProjectClick = {
                    navController.navigate("addProject")
                },
                navController = navController
            )
        }

        composable("projectDetailScreen/{projectId}") { backStackEntry ->
            // Recebe o projectId como String e converte para Int
            val projectId = backStackEntry.arguments?.getString("projectId")?.toIntOrNull()

            // Busca o projeto correspondente pelo ID
            val project = projectId?.let { id -> sampleProjects().firstOrNull { it.id == id } }

            // Verifica se o projeto existe e, se sim, exibe a tela de detalhes
            if (project != null) {
                ProjectDetailScreen(
                    projectId = projectId.toString(), // Passa o ID como String se necessário
                    navController = navController,
                    project = project
                )
            }
        }

        // Detalhamento da Tarefa
        composable("taskDetail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
            val task = taskId?.let { id -> sampleTasks().firstOrNull { it.id == id } }

            if (task != null) {
                TaskDetailScreen(
                    taskId = taskId.toString(),
                    navController = navController,
                    task = task
                )
            }
        }

        // Cadastro de Tarefas
        composable("taskForm/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")

            TaskFormScreen(
                taskId = taskId,
                navController = navController,
                onSaveTask = { task ->
                    // Lógica de salvar tarefa
                }
            )
        }

    }
}

// Função de exemplo para criar uma lista de projetos
fun sampleProjects(): List<Project> {
    return listOf(
        Project(
            id = 1,
            name = "Projeto Kanban 1",
            tasks = listOf(
                Task(1, "Tarefa 1", "Descrição da Tarefa 1", TaskStatus.TODO),
                Task(2, "Tarefa 2", "Descrição da Tarefa 2", TaskStatus.DOING),
                Task(3, "Tarefa 3", "Descrição da Tarefa 3", TaskStatus.DONE)

            )
        ),
        Project(
            id = 2,
            name = "Projeto Kanban 2",
            tasks = listOf(
                Task(2, "Tarefa 2", "Descrição da Tarefa 2", TaskStatus.DOING),
                Task(3, "Tarefa 3", "Descrição da Tarefa 3", TaskStatus.DONE)

            )
        )
    )
}
fun sampleTasks(): List<Task> {
    return listOf(
        Task(1, "Tarefa 1", "Descrição da Tarefa 1", TaskStatus.TODO),
        Task(2, "Tarefa 2", "Descrição da Tarefa 2", TaskStatus.DOING),
        Task(3, "Tarefa 3", "Descrição da Tarefa 3", TaskStatus.DONE)
    )
}
