import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(onAddProjectSuccess: (Project) -> Unit) {
    var projectName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Adicionar Novo Projeto") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp) // Adiciona padding extra para evitar sobreposição
        ) {
            TextField(
                value = projectName,
                onValueChange = { projectName = it },
                label = { Text("Nome do Projeto") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newProject = Project(id = (0..1000).random(), name = projectName)
                    onAddProjectSuccess(newProject) // Retorna o novo projeto
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Projeto")
            }
        }
    }
}

