package com.example.kabanproject

import AddProjectScreen
import ProjectDetailScreen
import ProjectListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kabanproject.ui.loginScreen.LoginScreen
import com.example.kabanproject.ui.registerScreen.RegisterScreen
import com.example.kabanproject.navigation.sampleProjects

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavigationGraph(navController = navController)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
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

        // Tela de Lista de Projetos
        composable("projectList") {
            ProjectListScreen(
                projects = sampleProjects(),
                onProjectClick = { project ->
                    navController.navigate("projectDetail/${project.id}")
                },
                onAddProjectClick = {
                    navController.navigate("addProject")
                },
                navController = navController
            )
        }

        // Tela de Detalhes do Projeto
        composable("projectDetail/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")
            ProjectDetailScreen(projectId = projectId, navController = navController)
        }

        // Tela de Adição de Projetos
        composable("addProject") {
            AddProjectScreen(onAddProjectSuccess = {
                navController.navigate("projectList") {
                    popUpTo("projectList") { inclusive = true }
                }
            })
        }
    }
}