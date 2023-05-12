package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(
    private val openTaskDetailView:(task: Task) -> Unit):
    RecyclerView.Adapter<TaskListViewHolder>() {

    private var listTask : List<Task> = emptyList()

    fun submit(list: List<Task>){
        listTask = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }

    //Tamanho da minha lista
    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = listTask[position]
        holder.bind(task, openTaskDetailView)
    }
}

class TaskListViewHolder(
    private val view: View) : RecyclerView.ViewHolder(view){

    private val tvTitle = view.findViewById<TextView>(R.id.tv_task_title)
    private val tvDesc = view.findViewById<TextView>(R.id.tv_task_description)



    fun bind(task: Task,
             openTaskDetailView:(task: Task) -> Unit){
        tvTitle.text = task.title
        tvDesc.text = task.description

        view.setOnClickListener {
            openTaskDetailView.invoke(task)
        }
    }

}