import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.navigation.NavHostController
import com.example.kabanproject.model.Task
import com.example.kabanproject.model.TaskStatus

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    navController: NavHostController,  // Recebendo o navController
    onAddProjectSuccess: (Project) -> Unit,
    tasks: List<Task> = listOf()
) {
    var projectName by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Projeto") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) { // Botão de voltar
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE), // Cor do TopAppBar
                    titleContentColor = Color.White // Cor do conteúdo (texto e ícones)
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

            // Botão para salvar o projeto
            Button(
                onClick = {
                    if (projectName.text.isNotBlank()) {
                        val newProject = Project(id = (0..1000).random(), name = projectName.text,
                            tasks = tasks)
                        onAddProjectSuccess(newProject)
                        navController.navigate("projectList") // Navegar de volta para a lista de projetos
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)), // Cor do botão
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(50.dp) // Botão com altura maior para destaque
            ) {
                Text(text = "Salvar Projeto", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}