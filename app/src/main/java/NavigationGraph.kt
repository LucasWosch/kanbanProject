import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kabanproject.ui.loginScreen.LoginScreen
import com.example.kabanproject.ui.registerScreen.RegisterScreen


@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Tela de Login
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("projectList") {
                    popUpTo("login") { inclusive = true }
                }
            }, onRegisterClick = {
                navController.navigate("register")
            })
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
                    navController.navigate("projectDetailScreen/${project.id}")
                },
                onAddProjectClick = {
                    navController.navigate("addProject")
                },
                navController = navController // Passando navController para suporte ao botão de voltar
            )
        }

        // Tela de Detalhes do Projeto
        composable("projectDetailScreen/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")
            ProjectDetailScreen(
                projectId = projectId,
                navController = navController // Passando navController para suporte ao botão de voltar
            )
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

// Função de exemplo para criar uma lista de projetos
fun sampleProjects(): List<Project> {
    return listOf(
        Project(1, "Projeto Kanban 1"),
        Project(2, "Projeto Kanban 2"),
        Project(3, "Projeto Kanban 3")
    )
}


