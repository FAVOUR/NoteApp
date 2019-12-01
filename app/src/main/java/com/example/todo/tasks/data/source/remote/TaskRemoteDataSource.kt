package com.example.todo.tasks.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.todo.tasks.data.Result
import com.example.todo.tasks.data.Task
import com.example.todo.tasks.data.TasksDataSource
import kotlinx.coroutines.delay
import java.lang.Error
import java.lang.Exception

/**
 * Created by Olije Favour on 12/1/2019.
 *Copyright (c) 2019    All rights reserved.
 */



class TaskRemoteDataSource(): TasksDataSource {

    private val TIME_DELAY = 2000L



    private var TASK_SERVICE_DATA =LinkedHashMap<String,Task>(2)

  init {

  }

    private val observableTask =MutableLiveData<Result<List<Task>>>()

    override fun observeTasks(): LiveData<Result<List<Task>>> {
    return  observableTask
    }

    override suspend fun getTasks(): Result<List<Task>> {

        val tasks = TASK_SERVICE_DATA.values.toList()
        delay(TIME_DELAY)
       return Result.Success(tasks)
    }

    override suspend fun refreshTasks() {
        observableTask.value=getTasks()
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {

        return  observableTask.map { tasks ->

            when(tasks){

                is Result.Loading ->Result.Loading
                is Result.Error -> Result.Error(tasks.exception)
                is Result.Success ->{
                    val task =tasks.data.firstOrNull(){ it.id ==taskId  }
                    ?: return@map Result.Error(Exception("Data Not found "))

                Result.Success(task)

            }

            }
        }

    }

    override suspend fun getTask(taskId: String): Result<Task> {

        TASK_SERVICE_DATA[taskId]?.let {
            delay(TIME_DELAY)
            return Result.Success(it)
        }
        return Result.Error(Exception("Task Not Found "))
    }

    override suspend fun refreshTask(taskId: String) {

        refreshTasks()
    }

    override suspend fun saveTask(task: Task) {

        TASK_SERVICE_DATA[task.id]=task

    }

    override suspend fun completeTask(task: Task) {

        TASK_SERVICE_DATA[task.id]= Task(task.title,task.description,true)
    }

    override suspend fun completeTask(taskId: String) {
        TASK_SERVICE_DATA[taskId].let{

        }
    }

    override suspend fun activateTask(task: Task) {
        TASK_SERVICE_DATA[task.id]= Task(task.title,task.description,false)
    }

    override suspend fun activateTask(taskId: String) {

    }

    override suspend fun clearCompletedTasks() {
        TASK_SERVICE_DATA.filterValues {

            it.isCompleted
        }as LinkedHashMap<String,Task>
    }

    override suspend fun deleteAllTasks() {
        TASK_SERVICE_DATA.clear()
    }

    override suspend fun deleteTask(taskId: String) {
    TASK_SERVICE_DATA.remove(taskId)

    }
}