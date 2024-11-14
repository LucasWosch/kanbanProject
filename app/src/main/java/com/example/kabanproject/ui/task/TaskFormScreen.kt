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
import com.example.kabanproject.data.model.TaskStatus
import com.example.kabanproject.ui.viewModels.TaskViewModel
import com.example.kabanproject.ui.viewModels.ProjectViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    taskId: String?,
    projectId: String,
    navController: NavHostController,
    taskRepository: TaskViewModel,
    projectRepository: ProjectViewModel,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(TaskStatus.TODO) }
    val coroutineScope = rememberCoroutineScope()

    // Carrega a tarefa se taskId existir
    LaunchedEffect(taskId) {
        if (taskId != null && taskId != "new") {
            val task = taskRepository.getTaskById(taskId.toInt())
            task?.let {
                title = it.title
                description = it.description
                status = it.status
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == "new") "Nova Tarefa" else "Editar Tarefa") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Status:", fontSize = 16.sp)
            DropdownMenuStatusSelector(status = status, onStatusChange = { status = it })

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val task = Task(
                        id = taskId?.toInt() ?: 0,
                        title = title,
                        description = description,
                        status = status
                    )
                    coroutineScope.launch {
                        taskRepository.saveTaskToProject(task, projectId)
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Salvar Tarefa")
            }
        }
    }
}

@Composable
fun DropdownMenuStatusSelector(status: TaskStatus, onStatusChange: (TaskStatus) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Status: $status")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { onStatusChange(TaskStatus.TODO); expanded = false }, text = {
                Text(text = "A Fazer")
            })
            DropdownMenuItem(onClick = { onStatusChange(TaskStatus.DOING); expanded = false }, text = {
                Text(text = "Fazendo")
            })
            DropdownMenuItem(onClick = { onStatusChange(TaskStatus.DONE); expanded = false }, text = {
                Text(text = "Finalizado")
            })
        }
    }
}