package com.comunidadedevspace.taskbeats.data

import androidx.lifecycle.LiveData
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
    fun getAll(): LiveData<List<Task>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    @Query("DELETE from task")
    fun deleteAll()

    @Query("DELETE from task WHERE id =:id")
    fun deleteById(id:Int)
}