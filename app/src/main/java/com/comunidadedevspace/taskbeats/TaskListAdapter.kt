package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class TaskListAdapter(
    private val openTaskDetailView:(task: Task) -> Unit
): ListAdapter<Task, TaskListViewHolder>(TaskListAdapter){
    companion object: DiffUtil.ItemCallback<Task>(){
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, openTaskDetailView)
    }


}

class TaskListViewHolder(
    private val view: View) : RecyclerView.ViewHolder(view){

    private val tvTitle = view.findViewById<TextView>(R.id.tv_task_title)
    private val tvDesc = view.findViewById<TextView>(R.id.tv_task_description)



    fun bind(task: Task,
             openTaskDetailView:(task: Task) -> Unit){
        tvTitle.text = task.title
        tvDesc.text = "${task.id}- ${task.description}"

        view.setOnClickListener {
            openTaskDetailView.invoke(task)
        }
    }

}