import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailScreen(projectId: String?, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalhes do Projeto") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "ID do Projeto: $projectId", style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Aqui vão os detalhes e tarefas do projeto.")
        }
    }
}