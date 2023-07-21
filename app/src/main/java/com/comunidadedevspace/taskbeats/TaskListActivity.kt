package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
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


    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "taskbeats-database"
        ).build()
    }

   private  val dao by lazy {
       dataBase.taskDao()
   }

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
               insertIntoDataBase(task)
                listFromDataBase()
            }else if(taskAction.actionType == ActionType.UPDATE.name){
                updateIntoDataBase(task)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)


        listFromDataBase()
        ctnContent = findViewById(R.id.ctn_content)


        //RecyclerView
        val rvTasks = findViewById<RecyclerView>(R.id.rv_task_list)
        rvTasks.adapter = adapter
        adapter.submitList(taskList)

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openTaskListDetail(null)
        }
    }

    private fun insertIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.insert(task)
            listFromDataBase()
        }
    }

    private fun updateIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.update(task)
            listFromDataBase()
        }
    }

    private fun listFromDataBase(){
        CoroutineScope(IO).launch {
            val myDataBaseList: List<Task> = dao.getAll()
            adapter.submitList(myDataBaseList)
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

