package com.example.kabanproject.data.repos.project

import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.repos.project.IRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteRepository : IRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val projectCollection = firestore.collection("projects")

    // Listar todos os projetos em tempo real usando Flow
    override fun getAllProjects(): Flow<List<Project>> = callbackFlow {
        val listener = projectCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val projects = snapshot.documents.mapNotNull { it.toObject(Project::class.java) }
                trySend(projects).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    // Obter um projeto específico pelo ID
    override suspend fun getProjectById(projectId: Int): Project? {
        val document = projectCollection.document(projectId.toString()).get().await()
        return document.toObject(Project::class.java)
    }

    // Obter projetos com um status específico
    override fun getProjectsByStatus(status: String): Flow<List<Project>> = callbackFlow {
        val listener = projectCollection
            .whereEqualTo("status", status)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val projects = snapshot.documents.mapNotNull { it.toObject(Project::class.java) }
                    trySend(projects).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }

    // Inserir ou atualizar um projeto
    override suspend fun upsertProject(project: Project) {
        val document: DocumentReference = if (project.id == null) {
            project.id = getNextId()
            projectCollection.document(project.id.toString())
        } else {
            projectCollection.document(project.id.toString())
        }
        document.set(project).await()
    }

    // Excluir um projeto
    override suspend fun deleteProject(project: Project) {
        projectCollection.document(project.id.toString()).delete().await()
    }

    // Função auxiliar para obter o próximo ID único para novos projetos
    private suspend fun getNextId(): Int {
        val snapshot = projectCollection.get().await()
        val maxId = snapshot.documents.mapNotNull { it.getLong("id")?.toInt() }.maxOrNull() ?: 0
        return maxId + 1
    }
}
