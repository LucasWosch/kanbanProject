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
import com.example.kabanproject.navigation.NavigationGraph
import com.example.kabanproject.ui.loginScreen.LoginScreen
import com.example.kabanproject.ui.registerScreen.RegisterScreen
import com.example.kabanproject.navigation.sampleProjects

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationGraph()
        }
    }
}
