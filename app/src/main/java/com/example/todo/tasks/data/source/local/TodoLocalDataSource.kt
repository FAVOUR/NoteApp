package com.example.todo.tasks.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todo.tasks.data.Result
import com.example.todo.tasks.data.Task
import com.example.todo.tasks.data.TasksDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Olije Favour on 11/29/2019.
 *Copyright (c) 2019    All rights reserved.
 */


/**
 * Concrete implementation of a data source as a db.
 */


class TodoLocalDataSource internal constructor(

    private val tasksDao: TasksDao,

    private val coroutineDispatcher:CoroutineDispatcher=Dispatchers.IO

     ): TasksDataSource {



    override fun observeTasks(): LiveData<Result<List<Task>>> = tasksDao.observeTasks().map {
        Result.Success(it)

    }


    override suspend fun getTasks(): Result<List<Task>> {

        return withContext(coroutineDispatcher){
            Result.Success(tasksDao.getTasks())
        }
    }

    override suspend fun refreshTasks() {


    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
      return  tasksDao.observeTaskById(taskId).map {
            Result.Success(it)

        }
    }

    //This will run but on the Dispatchers.Default thread
    override suspend fun getTask(taskId: String): Result<Task> {

        return  tasksDao.getTaskById(taskId).let {
            Result.Success(it!!)
        }

//        return  withContext(coroutineDispatcher){
//            tasksDao.getTaskById(taskId).let {
//                Result.Success(it!!)
//
//            }
    }

    override suspend fun refreshTask(taskId: String) {

    }

    override suspend fun saveTask(task: Task) {
          tasksDao.insertTask(task)
    }

    override suspend fun completeTask(task: Task) {
        tasksDao.updateCompleted(task.id,true)

    }

    override suspend fun completeTask(taskId: String) {
        tasksDao.updateCompleted(taskId,true)
    }

    override suspend fun activateTask(task: Task) {
        tasksDao.updateCompleted(task.id, false)
    }
    override suspend fun activateTask(taskId: String) {
        tasksDao.updateCompleted(taskId, false)
    }

    override suspend fun clearCompletedTasks() {
        tasksDao.deleteCompletedTasks()
    }

    override suspend fun deleteAllTasks() {
         tasksDao.deleteTasks()
    }

    override suspend fun deleteTask(taskId: String) {
        tasksDao.deleteTaskById(taskId)
    }


}