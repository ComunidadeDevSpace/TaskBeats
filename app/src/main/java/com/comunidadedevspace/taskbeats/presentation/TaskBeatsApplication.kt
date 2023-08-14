package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.data.AppDataBase

    class TaskBeatsApplication: Application() {
        lateinit var dataBase: AppDataBase

        override fun onCreate() {
            super.onCreate()

            dataBase = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java, "taskbeats-database"
            ).build()
        }
}