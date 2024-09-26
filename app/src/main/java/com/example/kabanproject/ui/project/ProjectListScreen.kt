import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectListScreen(
    projects: List<Project>,
    onProjectClick: (Project) -> Unit,
    onAddProjectClick: () -> Unit,
    navController: NavHostController  // Caso queira o botão de voltar, passe o navController
) {
    // Estado para controlar a visibilidade do menu
    var isMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Meus Projetos", textAlign = TextAlign.Center,modifier=Modifier.fillMaxWidth(), color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProjectClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Projeto")
            }
        }
    ) { innerPadding ->
        // Adiciona padding para garantir que o conteúdo fique abaixo da TopBar
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Utilizando o padding do Scaffold para garantir que o conteúdo fique abaixo da TopAppBar
                .padding(16.dp)
        ) {
            items(projects) { project ->
                ProjectItem(
                    project = project,
                    onClick = { onProjectClick(project) }
                )
            }
        }
    }
}

@Composable
fun ProjectItem(project: Project, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp), // Aplicando elevação para criar a sombra
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = project.name,  // Passando o texto corretamente para o nome do projeto
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ID do Projeto: ${project.id}",  // Passando o texto corretamente para o ID do projeto
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
