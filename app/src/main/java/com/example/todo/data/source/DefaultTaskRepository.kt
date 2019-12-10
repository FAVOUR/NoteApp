package com.example.todo.data.source

import android.app.Application
import androidx.room.Room
import com.example.todo.data.Result
import com.example.todo.data.Task
import com.example.todo.data.source.local.TodoDatabase
import com.example.todo.data.source.local.TodoLocalDataSource
import com.example.todo.data.source.remote.TaskRemoteDataSource
import kotlinx.coroutines.*

/**
 * Created by Olije Favour on 12/1/2019.
 *Copyright (c) 2019    All rights reserved.
 */



//class DefaultTaskRepository( application: Application) {
class DefaultTaskRepository private constructor(application: Application)  {


    private val taskDataSource:TodoLocalDataSource
    private val taskRemoteDataSource:TaskRemoteDataSource
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    companion object{
      @Volatile

        //Can a variable by default be acessed by other threads ?
      private var INSTANCE : DefaultTaskRepository?=null

        fun getRepository(application:Application):DefaultTaskRepository{

            return INSTANCE ?: synchronized(this){

                DefaultTaskRepository(application).also {
                    INSTANCE=it

                }

            }

        }

    }


    init {

        var  database= Room.databaseBuilder(application,TodoDatabase::class.java,"task_db")
                           .build()

        taskDataSource = TodoLocalDataSource(database.taskDoa())
        taskRemoteDataSource= TaskRemoteDataSource()

    }



    suspend fun updateRemoteDataSource(taskIid: String){


       val remoteData= taskRemoteDataSource.getTask(taskIid)

        if(remoteData is Result.Success )
        taskDataSource.saveTask(remoteData.data)

    }


    suspend fun  updateRemoteDataSource(){
        val remoteDataSource = taskRemoteDataSource.getTasks()

        if(remoteDataSource is Result.Success){
            taskDataSource.deleteAllTasks()
            remoteDataSource.data.forEach { task->

                taskDataSource.saveTask(task)
            }
            }else if (remoteDataSource is Result.Error){

            remoteDataSource.exception
            }
        }



  suspend fun getTask(forceUpdate:Boolean =false,taskId:String): Result<Task> {

      if(forceUpdate){
          updateRemoteDataSource(taskId)
      }

      return  taskDataSource.getTask(taskId)

  }

    suspend fun getTasks(forceUpdate: Boolean=false):Result<List<Task>>{


        if (forceUpdate){
            try {
                updateRemoteDataSource()
            }catch (ex: Exception) {
                return Result.Error(ex)
            }

        }

        return taskRemoteDataSource.getTasks()

    }

    suspend fun saveTask(task:Task){

        coroutineScope {

            launch { taskRemoteDataSource.saveTask(task)  }
            launch { taskDataSource.saveTask(task) }
        }
    }

    suspend fun completeTask(task:Task){

        coroutineScope {
            launch { taskRemoteDataSource.completeTask(task)  }
            launch { taskDataSource.completeTask(task)  }

        }
    }

    suspend fun  completeTask(taskId: String){


        coroutineScope {
            launch { taskRemoteDataSource.completeTask(taskId) }
            launch { taskDataSource.completeTask(taskId) }

        }

    }


    suspend fun activateTask(task: Task) = withContext<Unit>(ioDispatcher) {
        coroutineScope {
            launch { taskRemoteDataSource.activateTask(task) }
            launch { taskDataSource.activateTask(task) }
        }
    }

    suspend fun activateTask(taskId: String) {
        withContext(ioDispatcher) {
            (getTaskWithId(taskId) as? Result.Success)?.let { it ->
                activateTask(it.data)
            }
        }
    }

    suspend fun clearCompletedTasks() {
        coroutineScope {
            launch { taskRemoteDataSource.clearCompletedTasks() }
            launch { taskDataSource.clearCompletedTasks() }
        }
    }

    suspend fun deleteAllTasks() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { taskRemoteDataSource.deleteAllTasks() }
                launch { taskDataSource.deleteAllTasks() }
            }
        }
    }

    suspend fun deleteTask(taskId: String) {
        coroutineScope {
            launch { taskRemoteDataSource.deleteTask(taskId) }
            launch { taskDataSource.deleteTask(taskId) }
        }
    }

    private suspend fun getTaskWithId(id: String): Result<Task> {
        return taskDataSource.getTask(id)
    }




}