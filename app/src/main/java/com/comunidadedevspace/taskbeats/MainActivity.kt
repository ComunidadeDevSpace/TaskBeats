package com.comunidadedevspace.taskbeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Kotlin
        val taskList= listOf<Task>(
            Task("Title0","Desc0"),
            Task("Title1","Desc1"),
            Task("Title2","Desc2"),
            Task("Title3","Desc3"),
            Task("Title4","Desc4"),
            Task("Title5","Desc5"),
            Task("Title0","Desc0"),
            Task("Title1","Desc1"),
            Task("Title2","Desc2"),
            Task("Title3","Desc3"),
            Task("Title4","Desc4"),
            Task("Title5","Desc5"),
        )

        //Adapter
        val adapter: TaskListAdapter = TaskListAdapter(taskList, ::openTaskDetailView)


        //RecyclerView
        val rvTasks = findViewById<RecyclerView>(R.id.rv_task_list)
        rvTasks.adapter = adapter
    }

    private fun openTaskDetailView(task: Task){
        val intent = TaskDetailActivity.start(this, task)
        startActivity(intent)
    }
}