import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "projectList") {
        composable("projectList") {
            ProjectListScreen(
                projects = sampleProjects(), // Dados de exemplo
                onProjectClick = { project ->
                    navController.navigate("projectDetail/${project.id}")
                },
                onAddProjectClick = {
                    navController.navigate("addProject")
                }
            )
        }
        composable("projectDetail/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")
            ProjectDetailScreen(projectId = projectId)
        }
        composable("addProject") {
            AddProjectScreen(onAddProjectSuccess = { newProject ->
                navController.navigate("projectList") // Navegar de volta após a criação
            })
        }
    }
}

fun sampleProjects(): List<Project> {
    return listOf(
        Project(1, "Projeto Kanban 1"),
        Project(2, "Projeto Kanban 2"),
        Project(3, "Projeto Kanban 3")
    )
}
