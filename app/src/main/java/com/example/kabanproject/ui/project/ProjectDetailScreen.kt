import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.kabanproject.R
import com.example.kabanproject.model.Task
import com.example.kabanproject.model.TaskStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailScreen(
    projectId: String?,
    navController: NavHostController,
    project: Project
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedSection by remember { mutableStateOf("Todas") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFE3F2FD))
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                // Opções no Drawer
                DrawerItem(
                    title = "Todas as Tarefas",
                    isSelected = selectedSection == "Todas",
                    onClick = {
                        selectedSection = "Todas"
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerItem(
                    title = "A Fazer",
                    isSelected = selectedSection == "A Fazer",
                    onClick = {
                        selectedSection = "A Fazer"
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerItem(
                    title = "Fazendo",
                    isSelected = selectedSection == "Fazendo",
                    onClick = {
                        selectedSection = "Fazendo"
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerItem(
                    title = "Finalizado",
                    isSelected = selectedSection == "Finalizado",
                    onClick = {
                        selectedSection = "Finalizado"
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Opção para navegação para Projetos
                DrawerItem(
                    title = "Projetos",
                    isSelected = false,
                    onClick = {
                        navController.navigate("projectList")
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Detalhes do Projeto", color = Color.White)
                        },
                        navigationIcon = {
                            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White // Ícone de menu branco
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* Lógica de exclusão ou outra ação */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Excluir Projeto",
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {

                    when (selectedSection) {
                        "A Fazer" -> {
                            item {
                                SectionHeaderWithAddButton(
                                    title = "Tarefas - A Fazer",
                                    onAddClick = { navController.navigate("taskForm/0") } // Navega para a tela de cadastro de tarefa
                                )
                            }
                            items(project.tasks.filter { it.status == TaskStatus.TODO }) { task ->
                                TaskItem(task = task, onClick = {
                                    navController.navigate("taskDetail/${task.id}") // Navega para a tela de detalhamento da tarefa
                                })
                            }
                        }
                        "Fazendo" -> {
                            item {
                                SectionHeaderWithAddButton(
                                    title = "Tarefas - Fazendo",
                                    onAddClick = { navController.navigate("taskForm/0") } // Navega para a tela de cadastro de tarefa
                                )
                            }
                            items(project.tasks.filter { it.status == TaskStatus.DOING }) { task ->
                                TaskItem(task = task, onClick = {
                                    navController.navigate("taskDetail/${task.id}") // Navega para a tela de detalhamento da tarefa
                                })
                            }
                        }
                        "Finalizado" -> {
                            item {
                                SectionHeaderWithAddButton(
                                    title = "Tarefas - Finalizadas",
                                    onAddClick = { navController.navigate("taskForm/0") } // Navega para a tela de cadastro de tarefa
                                )
                            }
                            items(project.tasks.filter { it.status == TaskStatus.DONE }) { task ->
                                TaskItem(task = task, onClick = {
                                    navController.navigate("taskDetail/${task.id}") // Navega para a tela de detalhamento da tarefa
                                })
                            }
                        }
                        else -> {
                            item {
                                SectionHeaderWithAddButton(
                                    title = "Tarefas - A Fazer",
                                    onAddClick = { navController.navigate("taskForm/0") } // Navega para a tela de cadastro de tarefa
                                )
                            }
                            items(project.tasks.filter { it.status == TaskStatus.TODO }) { task ->
                                TaskItem(task = task, onClick = {
                                    navController.navigate("taskDetail/${task.id}") // Navega para a tela de detalhamento da tarefa
                                })
                            }

                            item {
                                SectionHeaderWithAddButton(
                                    title = "Tarefas - Fazendo",
                                    onAddClick = { navController.navigate("taskForm/0") } // Navega para a tela de cadastro de tarefa
                                )
                            }
                            items(project.tasks.filter { it.status == TaskStatus.DOING }) { task ->
                                TaskItem(task = task, onClick = {
                                    navController.navigate("taskDetail/${task.id}") // Navega para a tela de detalhamento da tarefa
                                })
                            }

                            item {
                                SectionHeaderWithAddButton(
                                    title = "Tarefas - Finalizadas",
                                    onAddClick = { navController.navigate("taskForm/0") } // Navega para a tela de cadastro de tarefa
                                )
                            }
                            items(project.tasks.filter { it.status == TaskStatus.DONE }) { task ->
                                TaskItem(task = task, onClick = {
                                    navController.navigate("taskDetail/${task.id}") // Navega para a tela de detalhamento da tarefa
                                })
                            }
                        }
                    }
                }
            }
        }
    )
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
fun DrawerItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFFBBDEFB) else Color.Transparent // Cor de fundo para o item selecionado
    val textColor = if (isSelected) Color(0xFF0D47A1) else Color.Black // Cor do texto para o item selecionado

    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor) // Aplica a cor de fundo dependendo da seleção
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = textColor, // Aplica a cor do texto dependendo da seleção
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
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