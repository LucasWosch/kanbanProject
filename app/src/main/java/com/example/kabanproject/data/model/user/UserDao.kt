package com.example.kabanproject.data.model.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Inserir um novo usuário ou atualizar se já existir (upsert)
    @Upsert
    suspend fun upsertUser(user: User)

    // Excluir um usuário
    @Delete
    suspend fun deleteUser(user: User)

    // Obter todos os usuários (usando Flow para observar as mudanças em tempo real)
    @Query("SELECT * FROM User")
    fun getAllUsers(): Flow<List<User>>

    // Obter um usuário específico pelo ID
    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    // Obter um usuário específico pelo email
    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
}
