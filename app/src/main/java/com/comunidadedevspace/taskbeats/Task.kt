package com.comunidadedevspace.taskbeats

import java.io.Serializable
import android.app.ActivityManager.TaskDescription
import android.icu.text.CaseMap.Title
import java.sql.RowId

data class Task(
    val id: Int,
    val title: String,
    val description: String
    ): Serializable
