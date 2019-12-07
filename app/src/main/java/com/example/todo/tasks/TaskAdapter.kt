package com.example.todo.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.AllTaskBinding
import com.example.todo.data.Task

/**
 * Created by Olije Favour on 11/26/2019.
 *Copyright (c) 2019    All rights reserved.
 */



class TaskAdapter(val taskViewModel: TaskViewModel):ListAdapter<Task,TaskAdapter.TaskViewHolder>(TaskDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{

        return  from(parent)

    }

//    override fun getItemCount(): Int {
//
//
//
//    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val item = getItem(position)

        holder.bind(taskViewModel, item)
    }

     class TaskViewHolder(val allTaskBinding: AllTaskBinding):RecyclerView.ViewHolder(allTaskBinding.root){


           fun bind (taskViewModel: TaskViewModel,task:Task){


               allTaskBinding.duties=task
               allTaskBinding.executePendingBindings()


           }

    }


    companion object{

           fun from(parent: ViewGroup): TaskViewHolder {
               val layoutInflater:LayoutInflater= LayoutInflater.from(parent.context)

              val allTaskBinding:AllTaskBinding = AllTaskBinding.inflate(layoutInflater,parent,false)

               return TaskViewHolder(allTaskBinding)
           }
    }

      class TaskDiffUtils:DiffUtil.ItemCallback<Task>(){

          override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {

              return oldItem.id==newItem.id
          }


          override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {

              return oldItem.equals(newItem)
          }

      }
}