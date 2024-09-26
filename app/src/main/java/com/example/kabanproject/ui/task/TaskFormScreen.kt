package com.example.kabanproject.ui.task

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
@Composable
fun TaskFormScreen(
    taskId: String?,
    navController: NavHostController,
    onSaveTask: (Task) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") } // Variável de estado para descrição
    var status by remember { mutableStateOf(TaskStatus.TODO) } // Status inicial

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Cadastro de Tarefa", color = Color.White) },
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
                modifier = Modifier.fillMaxWidth() // Campo para editar a descrição
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Seletor de status
            Text(text = "Status:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenuStatusSelector(status = status, onStatusChange = { status = it })

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    val task = Task(id = taskId?.toInt() ?: 0, title = title, description = description, status = status) // Criação da tarefa com descrição
                    onSaveTask(task)
                    navController.navigateUp()
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