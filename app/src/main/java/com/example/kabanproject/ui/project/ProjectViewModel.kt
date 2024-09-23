import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectViewModel : ViewModel() {
    // Lista de projetos mockada
    private val _projects = MutableLiveData<List<Project>>(
        listOf(
            Project(1, "Projeto Kanban 1"),
            Project(2, "Projeto Kanban 2"),
            Project(3, "Projeto Kanban 3")
        )
    )
    val projects: LiveData<List<Project>> = _projects
}
