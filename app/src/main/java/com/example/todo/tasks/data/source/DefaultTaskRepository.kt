package com.example.todo.tasks.data.source

import android.app.Application
import androidx.room.Room
import com.example.todo.tasks.data.source.local.TodoDatabase
import com.example.todo.tasks.data.source.local.TodoLocalDataSource

/**
 * Created by Olije Favour on 12/1/2019.
 *Copyright (c) 2019    All rights reserved.
 */



class DefaultTaskRepository( application: Application) {

    private val taskDataSource:TodoLocalDataSource

    companion object{
      @Volatile

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

    }







}