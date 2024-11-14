import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.data.model.TaskStatus
import com.example.kabanproject.ui.viewModels.ProjectViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailScreen(
    projectId: String?,
    navController: NavHostController,
    projectRepository: ProjectViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedSection by remember { mutableStateOf("Todas") }
    var project by remember { mutableStateOf<Project?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Carrega o projeto a partir do projectId
    LaunchedEffect(projectId) {
        coroutineScope.launch {
            try {
                project = projectId?.toIntOrNull()?.let { projectRepository.getProjectById(it) }
                if (project == null) {
                    errorMessage = "Projeto não encontrado"
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar o projeto"
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Conteúdo do Drawer (igual ao código original)
        },
        content = {
            Scaffold(
                topBar = {
                    // Barra superior (igual ao código original)
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    if (project != null) {
                        when (selectedSection) {
                            "A Fazer" -> {
                                item {
                                    SectionHeaderWithAddButton(
                                        title = "Tarefas - A Fazer",
                                        onAddClick = {
                                            navController.navigate("taskForm/new/${projectId ?: ""}")
                                        }
                                    )
                                }
                                items(project!!.tasks.filter { it.status == TaskStatus.TODO }) { task ->
                                    TaskItem(task = task, onClick = {
                                        navController.navigate("taskForm/${task.id}/${projectId ?: ""}")
                                    })
                                }
                            }
                            "Fazendo" -> {
                                item {
                                    SectionHeaderWithAddButton(
                                        title = "Tarefas - Fazendo",
                                        onAddClick = {
                                            navController.navigate("taskForm/new/${projectId ?: ""}")
                                        }
                                    )
                                }
                                items(project!!.tasks.filter { it.status == TaskStatus.DOING }) { task ->
                                    TaskItem(task = task, onClick = {
                                        navController.navigate("taskForm/${task.id}/${projectId ?: ""}")
                                    })
                                }
                            }
                            "Finalizado" -> {
                                item {
                                    SectionHeaderWithAddButton(
                                        title = "Tarefas - Finalizadas",
                                        onAddClick = {
                                            navController.navigate("taskForm/new/${projectId ?: ""}")
                                        }
                                    )
                                }
                                items(project!!.tasks.filter { it.status == TaskStatus.DONE }) { task ->
                                    TaskItem(task = task, onClick = {
                                        navController.navigate("taskForm/${task.id}/${projectId ?: ""}")
                                    })
                                }
                            }
                            else -> {
                                // Exibe todas as seções, usando o mesmo padrão
                                item {
                                    SectionHeaderWithAddButton(
                                        title = "Tarefas - A Fazer",
                                        onAddClick = {
                                            navController.navigate("taskForm/new/${projectId ?: ""}")
                                        }
                                    )
                                }
                                items(project!!.tasks.filter { it.status == TaskStatus.TODO }) { task ->
                                    TaskItem(task = task, onClick = {
                                        navController.navigate("taskForm/${task.id}/${projectId ?: ""}")
                                    })
                                }
                                item {
                                    SectionHeaderWithAddButton(
                                        title = "Tarefas - Fazendo",
                                        onAddClick = {
                                            navController.navigate("taskForm/new/${projectId ?: ""}")
                                        }
                                    )
                                }
                                items(project!!.tasks.filter { it.status == TaskStatus.DOING }) { task ->
                                    TaskItem(task = task, onClick = {
                                        navController.navigate("taskForm/${task.id}/${projectId ?: ""}")
                                    })
                                }
                                item {
                                    SectionHeaderWithAddButton(
                                        title = "Tarefas - Finalizadas",
                                        onAddClick = {
                                            navController.navigate("taskForm/new/${projectId ?: ""}")
                                        }
                                    )
                                }
                                items(project!!.tasks.filter { it.status == TaskStatus.DONE }) { task ->
                                    TaskItem(task = task, onClick = {
                                        navController.navigate("taskForm/${task.id}/${projectId ?: ""}")
                                    })
                                }
                            }
                        }
                    } else {
                        item {
                            Text(
                                text = errorMessage ?: "Carregando...",
                                fontSize = 18.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DrawerItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFFBBDEFB) else Color.Transparent
    val textColor = if (isSelected) Color(0xFF0D47A1) else Color.Black

    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SectionHeaderWithAddButton(title: String, onAddClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )

        IconButton(onClick = onAddClick) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Adicionar Tarefa",
                tint = Color(0xFF6200EE)
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = task.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
