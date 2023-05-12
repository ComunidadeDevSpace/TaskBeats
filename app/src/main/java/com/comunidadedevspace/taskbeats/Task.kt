package com.comunidadedevspace.taskbeats

import java.io.Serializable
import android.app.ActivityManager.TaskDescription
import android.icu.text.CaseMap.Title

data class Task(val title: String, val description: String): Serializable
