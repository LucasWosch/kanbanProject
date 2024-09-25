import android.annotation.SuppressLint
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectListScreen(
    projects: List<Project>,
    onProjectClick: (Project) -> Unit,
    onAddProjectClick: () -> Unit,
    navController: NavHostController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProjectClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Projeto")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(projects) { project ->
                ProjectItem(project = project, onClick = { onProjectClick(project) })
            }
        }
    }
}

@Composable
fun ProjectItem(project: Project, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = project.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}