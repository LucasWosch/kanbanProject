import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.ui.viewModels.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String?,
    navController: NavHostController,
    taskRepository: TaskViewModel // Adiciona o repositório de tarefas
) {
    val coroutineScope = rememberCoroutineScope()
    var task by remember { mutableStateOf<Task?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Carrega a tarefa a partir do taskId
    LaunchedEffect(taskId) {
        coroutineScope.launch {
            try {
                task = taskId?.toIntOrNull()?.let { taskRepository.getTaskById(it) }
                if (task == null) {
                    errorMessage = "Tarefa não encontrada"
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar a tarefa"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detalhes da Tarefa", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            task?.let {
                Text(text = "Título: ${it.title}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Status: ${it.status}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Descrição: ${it.description}", fontSize = 16.sp)

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { navController.navigate("taskForm/${it.id}") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Editar Tarefa")
                }
            } ?: run {
                Text(
                    text = errorMessage ?: "Carregando...",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
