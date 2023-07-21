package com.comunidadedevspace.taskbeats

import java.io.Serializable
import android.app.ActivityManager.TaskDescription
import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.RowId

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
    ): Serializable
