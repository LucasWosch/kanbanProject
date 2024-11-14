package com.example.kabanproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kabanproject.data.model.project.Project
import com.example.kabanproject.data.model.task.Task
import com.example.kabanproject.data.model.user.User
import com.example.kabanproject.data.model.project.ProjectDao
import com.example.kabanproject.data.model.task.TaskDao
import com.example.kabanproject.data.model.user.UserDao

// Definindo o banco de dados com as entidades Project, Task e User
@Database(entities = [Project::class, Task::class, User::class], version = 1)
abstract class KabanDatabase : RoomDatabase() {
    abstract fun getProjectDao(): ProjectDao
    abstract fun getTaskDao(): TaskDao
    abstract fun getUserDao(): UserDao
}

// Função para abrir o banco de dados
fun abrirBanco(context: Context): KabanDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        KabanDatabase::class.java,
        "kaban_database.db"
    ).build()
}
