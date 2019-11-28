package com.example.todo.tasks

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Olije Favour on 11/26/2019.
 *Copyright (c) 2019    All rights reserved.
 */



class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

    }

    override fun getItemCount(): Int {
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    }

    inner class TaskViewHolder(view:View):RecyclerView.ViewHolder(view)
}