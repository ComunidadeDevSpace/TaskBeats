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
        Task(2, "Title2", "Desc1"),
        Task(3, "Title3", "Desc1"),
    )

    //Adapter
    private val adapter: TaskListAdapter = TaskListAdapter(::openTaskDetailView)


    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            //removendo item da lista kotlin
            taskList.remove(task)

            //atualizar o adapter
            adapter.submit(taskList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



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

