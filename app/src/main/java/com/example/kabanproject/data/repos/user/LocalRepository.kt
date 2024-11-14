package com.example.kabanproject.data.repos.user

import com.example.kabanproject.data.model.user.User
import com.example.kabanproject.data.model.user.UserDao
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val userDao: UserDao) : IRepository {

    override suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
