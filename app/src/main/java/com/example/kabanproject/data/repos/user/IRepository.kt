package com.example.kabanproject.data.repos.user

import com.example.kabanproject.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun upsertUser(user: User)
    suspend fun deleteUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    suspend fun getUserById(userId: Int): User?
    suspend fun getUserByEmail(email: String): User?
}
