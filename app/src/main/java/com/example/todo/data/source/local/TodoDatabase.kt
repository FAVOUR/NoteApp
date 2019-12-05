package com.example.todo.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.data.Task

/**
 * Created by Olije Favour on 11/28/2019.
 *Copyright (c) 2019    All rights reserved.
 */

// *Copyright (c) 2019  Itex Integrated Services  All rights reserved.

@Database(entities = [Task::class],version = 1,exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {

   abstract fun taskDoa():TasksDao
}