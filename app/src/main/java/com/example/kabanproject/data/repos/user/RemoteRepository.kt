package com.example.kabanproject.data.repos.user

import com.example.kabanproject.data.model.user.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteRepository : IRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")

    override fun getAllUsers(): Flow<List<User>> = callbackFlow {
        val listener = userCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val users = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
                trySend(users).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun getUserById(userId: Int): User? {
        val document = userCollection.document(userId.toString()).get().await()
        return document.toObject(User::class.java)
    }

    override suspend fun getUserByEmail(email: String): User? {
        val document = userCollection
            .whereEqualTo("email", email)
            .get()
            .await()
            .documents
            .firstOrNull()
        return document?.toObject(User::class.java)
    }

    override suspend fun upsertUser(user: User) {
        val document: DocumentReference = if (user.id == null) {
            user.id = getNextId()
            userCollection.document(user.id.toString())
        } else {
            userCollection.document(user.id.toString())
        }
        document.set(user).await()
    }

    override suspend fun deleteUser(user: User) {
        userCollection.document(user.id.toString()).delete().await()
    }

    private suspend fun getNextId(): Int {
        val snapshot = userCollection.get().await()
        val maxId = snapshot.documents.mapNotNull { it.getLong("id")?.toInt() }.maxOrNull() ?: 0
        return maxId + 1
    }
}
