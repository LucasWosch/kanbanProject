package com.example.kabanproject.navigation

import AddProjectScreen
import ProjectDetailScreen
import ProjectListScreen
import TaskDetailScreen
import TaskFormScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kabanproject.ui.loginScreen.LoginScreen
import com.example.kabanproject.ui.registerScreen.RegisterScreen
import com.example.kabanproject.ui.viewModels.ProjectViewModel
import com.example.kabanproject.ui.viewModels.TaskViewModel
import com.example.kabanproject.ui.viewModels.UserViewModel

@Composable
fun NavigationGraph(
    projectViewModel: ProjectViewModel,
    taskViewModel: TaskViewModel,
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Tela de Login
        composable("login") {
            LoginScreen(
                userRepository = userViewModel,  // Passando userRepository para a tela de login
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
            RegisterScreen(
                userRepository = userViewModel,  // Passando userRepository para a tela de registro
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Tela de Adição de Projetos
        composable("addProject") {
            AddProjectScreen(
                projectViewModel = projectViewModel,  // Passando projectRepository para a tela de adição de projetos
                navController = navController,
                onAddProjectSuccess = {
                    navController.navigate("projectList") {
                        popUpTo("projectList") { inclusive = true }
                    }
                }
            )
        }

        // Tela de Lista de Projetos
        composable("projectList") {
            ProjectListScreen(
                projectViewModel = projectViewModel,  // Passando projectRepository para a tela de lista de projetos
                onProjectClick = { project ->
                    navController.navigate("projectDetailScreen/${project.id}")
                },
                onAddProjectClick = {
                    navController.navigate("addProject")
                },
                navController = navController
            )
        }

        // Tela de Detalhes do Projeto
        composable("projectDetailScreen/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")

            ProjectDetailScreen(
                projectId = projectId,
                navController = navController,
                projectRepository = projectViewModel  // Passa o repositório para carregamento dentro da tela
            )
        }


        // Tela de Detalhes da Tarefa
        composable("taskDetail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")

            TaskDetailScreen(
                taskId = taskId,
                navController = navController,
                taskRepository = taskViewModel  // Passa o repositório para que o carregamento ocorra dentro da tela
            )
        }


        // Tela de Cadastro de Tarefas
        composable("taskForm/{taskId}/{projectId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""

            TaskFormScreen(
                taskId = taskId,
                projectId = projectId,
                taskRepository = taskViewModel,
                projectRepository = projectViewModel,
                navController = navController
            )
        }


    }
}

