package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView

class TaskDetailActivity : AppCompatActivity() {

    private  var task: Task? = null
    companion object{
      private const val TASK_DETAIL_EXTRA = "task.extra.detail"
        fun start(context: Context, task: Task?): Intent{
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(TASK_DETAIL_EXTRA, task)
                }
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //Recuperar a task
         task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?


        //Recuperar campo do XML
        val tvTitle = findViewById<TextView>(R.id.tv_task_title_detail)


        //setar um novo texto na tela
        tvTitle.text = task?.title  ?: "Adicione uma tarefa"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.delete_task -> {

                if(task != null) {
                    val intent = Intent()
                        .apply {
                            val actionType = ActionType.DELETE
                            val taskAction = TaskAction(task!!, actionType)
                            putExtra(TASK_ACTION_RESULT, taskAction)
                        }
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}