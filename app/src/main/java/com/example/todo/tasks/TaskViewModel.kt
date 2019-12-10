package com.example.todo.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todo.data.source.DefaultTaskRepository

/**
 * Created by Olije Favour on 12/1/2019.
 *Copyright (c) 2019    All rights reserved.
 */



class TaskViewModel(application: Application):AndroidViewModel(application) {


   private val taskRepository: DefaultTaskRepository
}