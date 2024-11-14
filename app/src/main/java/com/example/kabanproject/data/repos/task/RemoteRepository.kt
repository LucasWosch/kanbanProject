package com.example.kabanproject.data.repos.task

import com.example.kabanproject.data.model.task.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteRepository : IRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val taskCollection = firestore.collection("tasks")

    override fun getAllTasks(): Flow<List<Task>> = callbackFlow {
        val listener = taskCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val tasks = snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
                trySend(tasks).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        val document = taskCollection.document(taskId.toString()).get().await()
        return document.toObject(Task::class.java)
    }

    override fun getTasksByStatus(status: String): Flow<List<Task>> = callbackFlow {
        val listener = taskCollection
            .whereEqualTo("status", status)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val tasks = snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
                    trySend(tasks).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getTasksByProjectId(projectId: Int): Flow<List<Task>> = callbackFlow {
        val listener = taskCollection
            .whereEqualTo("projectId", projectId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val tasks = snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
                    trySend(tasks).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun upsertTask(task: Task) {
        val document: DocumentReference = if (task.id == null) {
            task.id = getNextId()
            taskCollection.document(task.id.toString())
        } else {
            taskCollection.document(task.id.toString())
        }
        document.set(task).await()
    }

    override suspend fun deleteTask(task: Task) {
        taskCollection.document(task.id.toString()).delete().await()
    }

    private suspend fun getNextId(): Int {
        val snapshot = taskCollection.get().await()
        val maxId = snapshot.documents.mapNotNull { it.getLong("id")?.toInt() }.maxOrNull() ?: 0
        return maxId + 1
    }
}
