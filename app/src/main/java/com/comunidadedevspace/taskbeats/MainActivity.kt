package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    //Kotlin
    private val taskList = arrayListOf(
        Task(0, "Title0", "Desc0"),
        Task(1, "Title1", "Desc1"),
    )
   private val  adapter: TaskListAdapter = TaskListAdapter(::openTaskDetailView)
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task
            taskList.remove(task)
            adapter.submit(taskList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Adapter
        val adapter: TaskListAdapter = TaskListAdapter(::openTaskDetailView)
        adapter.submit(taskList)

        //RecyclerView
        val rvTasks = findViewById<RecyclerView>(R.id.rv_task_list)
        rvTasks.adapter = adapter
    }

    private fun openTaskDetailView(task: Task) {
        val intent = TaskDetailActivity.start(this, task)

        startForResult.launch(intent)

    }
}

sealed class ActionType : java.io.Serializable {
    object DELETE : ActionType()
    object UPDATE : ActionType()
    object CREATE : ActionType()

}

data class TaskAction(
    val task: Task,
    val actionType: ActionType
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"

