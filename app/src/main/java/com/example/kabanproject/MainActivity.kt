package com.example.kabanproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.kabanproject.data.abrirBanco
import com.example.kabanproject.data.model.project.ProjectDao
import com.example.kabanproject.data.model.task.TaskDao
import com.example.kabanproject.data.model.user.UserDao
import com.example.kabanproject.data.repos.*
import com.example.kabanproject.data.repos.project.IRepository as IProjectRepository
import com.example.kabanproject.data.repos.task.IRepository as ITaskRepository
import com.example.kabanproject.data.repos.user.IRepository as IUserRepository
import com.example.kabanproject.data.repos.project.LocalRepository as LocalProjectRepository
import com.example.kabanproject.data.repos.task.LocalRepository as LocalTaskRepository
import com.example.kabanproject.data.repos.user.LocalRepository as LocalUserRepository
import com.example.kabanproject.data.repos.project.RemoteRepository as RemoteProjectRepository
import com.example.kabanproject.data.repos.task.RemoteRepository as RemoteTaskRepository
import com.example.kabanproject.data.repos.user.RemoteRepository as RemoteUserRepository
import com.example.kabanproject.navigation.NavigationGraph
import com.example.kabanproject.ui.viewModels.ProjectViewModel
import com.example.kabanproject.ui.viewModels.TaskViewModel
import com.example.kabanproject.ui.viewModels.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Defina 'isLocal' para escolher entre repositórios locais (Room) ou remotos (Firestore)
            val isLocal = false

            // Inicializando os repositórios com base em `isLocal`
            val projectRepository: IProjectRepository
            val taskRepository: ITaskRepository
            val userRepository: IUserRepository

            if (isLocal) {
                // Inicialização local: Room Database
                val db = remember { abrirBanco(this) }

                // DAOs locais
                val projectDao: ProjectDao = db.getProjectDao()
                val taskDao: TaskDao = db.getTaskDao()
                val userDao: UserDao = db.getUserDao()

                // Repositórios locais
                projectRepository = LocalProjectRepository(projectDao)
                taskRepository = LocalTaskRepository(taskDao)
                userRepository = LocalUserRepository(userDao)
            } else {
                // Inicialização remota: Firebase Firestore
                projectRepository = RemoteProjectRepository()
                taskRepository = RemoteTaskRepository()
                userRepository = RemoteUserRepository()
            }

            val projectViewModel = ProjectViewModel(projectRepository)
            val taskViewModel = TaskViewModel(taskRepository, projectRepository)
            val userViewModel = UserViewModel(userRepository)

            // Passa os repositórios para a NavigationGraph
            NavigationGraph(
                projectViewModel,
                taskViewModel,
                userViewModel
            )
        }
    }

}
