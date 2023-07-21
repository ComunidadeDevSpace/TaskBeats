package com.comunidadedevspace.taskbeats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Query("Select * from task")
    fun getAll(): List<Task>

    //Update encontrar pelo id a tarefa que queremos

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)
}