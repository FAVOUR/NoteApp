package com.example.todo.tasks.data.source.local

import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Olije Favour on 11/28/2019.
 *Copyright (c) 2019    All rights reserved.
 */

// *Copyright (c) 2019  Itex Integrated Services  All rights reserved.


abstract class TodoDatabase: RoomDatabase() {

   abstract fun taskDoa():TasksDao
}