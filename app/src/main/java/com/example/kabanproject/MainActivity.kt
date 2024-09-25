package com.example.kabanproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kabanproject.ui.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Chamando a função AppNavigator
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }

    // Definindo o NavHost
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "login"
    ) {
        // Rota para a tela de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    isLoggedIn = true
                    // Navega para a tela "home" após o login com sucesso
                    navController.navigate("home") {
                        // Remove a tela de login da pilha de navegação para evitar voltar a ela
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Rota para a tela principal (Home)
        composable("home") {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    Text(text = "Bem-vindo à Home Screen!")
}