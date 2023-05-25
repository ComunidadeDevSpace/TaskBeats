package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {


    //Kotlin
    private var taskList = arrayListOf(
        Task(0, "Academia", "Treino de Força"),
        Task(1, "Mercado", "Comprar arroz e feijão"),
        Task(2, "DevSpace", "Estudar DevSpace"),
        Task(3, "Trabalho", "Lavar uniforme"),
    )

    private lateinit var ctnContent: LinearLayout

    //Adapter
    private val adapter: TaskListAdapter = TaskListAdapter(::onListItemClicked)


    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            if(taskAction.actionType == ActionType.DELETE.name){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }

                //removendo item da lista kotlin

                newList.remove(task)

                showMessage(ctnContent,"Item deleted ${task.title}")
                if(newList.size == 0){
                    ctnContent.visibility = View.VISIBLE
                }

                //atualizar o adapter
                adapter.submitList(newList)

                taskList = newList
            }else if(taskAction.actionType == ActionType.CREATE.name) {
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                //add item da lista kotlin

                newList.add(task)
                showMessage(ctnContent, "Item deleted ${task.title}")

                //atualizar o adapter
                adapter.submitList(newList)
                taskList = newList
            }else if(taskAction.actionType == ActionType.UPDATE.name){
                val tempEmptyList = arrayListOf<Task>()
                taskList.forEach{
                    if(it.id == task.id){
                        val newItem = Task(it.id, task.title, task.description)
                        tempEmptyList.add(newItem)
                    }else{
                        tempEmptyList.add(it)
                    }
                }

                showMessage(ctnContent, "Item updated ${task.title}")
                adapter.submitList(tempEmptyList)
                taskList = tempEmptyList
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        ctnContent = findViewById(R.id.ctn_content)

        adapter.submitList(taskList)


        //RecyclerView
        val rvTasks = findViewById<RecyclerView>(R.id.rv_task_list)
        rvTasks.adapter = adapter
        adapter.submitList(taskList)

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openTaskListDetail(null)
        }
    }

    private fun showMessage(view: View, message:String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun onListItemClicked(task: Task) {
        openTaskListDetail(task)

    }

    private fun openTaskListDetail(task: Task?){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }
}

//CRUD
//CREATE
//READ
//UPDATE
//DELETE

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(
    val task: Task,
    val actionType: String
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"

