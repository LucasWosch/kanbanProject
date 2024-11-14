import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.ui.viewModels.ProjectViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    projectViewModel: ProjectViewModel,  // Recebendo a ViewModel
    navController: NavHostController,
    tasks: List<Task> = listOf(),
    onAddProjectSuccess: (Project) -> Unit
) {
    var projectName by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Projeto") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Novo Projeto",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // Campo de Texto do Nome do Projeto
            TextField(
                value = projectName,
                onValueChange = { projectName = it },
                label = { Text("Nome do Projeto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Exibir mensagem de erro, se houver
            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Botão para salvar o projeto
            Button(
                onClick = {
                    if (projectName.text.isNotBlank()) {
                        coroutineScope.launch {
                            val newProject = Project(
                                id = (0..1000).random(),
                                name = projectName.text,
                                tasks = tasks
                            )
                            projectViewModel.saveProject(newProject) // Chama o método `saveProject`
                            navController.navigate("projectList") // Navegar de volta para a lista de projetos
                        }
                    } else {
                        errorMessage = "O nome do projeto não pode estar em branco"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(50.dp)
            ) {
                Text(text = "Salvar Projeto", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
