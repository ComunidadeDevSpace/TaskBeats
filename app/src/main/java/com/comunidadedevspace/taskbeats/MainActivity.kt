package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {


    //Kotlin
    private val taskList = arrayListOf(
        Task(0, "Academia", "Treino de Força"),
        Task(1, "Mercado", "Comprar arroz e feijão"),
        Task(2, "DevSpace", "Estudar DevSpace"),
        Task(3, "Trabalho", "Lavar uniforme"),
    )

    private lateinit var ctnContent: LinearLayout

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

            if(taskList.size == 0){
                ctnContent.visibility = View.VISIBLE
            }

            //atualizar o adapter
            adapter.submitList(taskList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctnContent = findViewById(R.id.ctn_content)

        adapter.submitList(taskList)


        //RecyclerView
        val rvTasks = findViewById<RecyclerView>(R.id.rv_task_list)
        rvTasks.adapter = adapter
        adapter.submitList(taskList)
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

