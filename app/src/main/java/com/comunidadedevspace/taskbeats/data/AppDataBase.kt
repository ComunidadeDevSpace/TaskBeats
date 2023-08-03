package com.comunidadedevspace.taskbeats.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
