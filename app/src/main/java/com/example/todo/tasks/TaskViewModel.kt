package com.example.todo.tasks

import android.app.Application
import androidx.lifecycle.*
import com.example.todo.data.Result
import com.example.todo.data.Task
import com.example.todo.data.source.DefaultTaskRepository
import kotlinx.coroutines.launch
import com.example.todo.data.Result.Success

/**
 * Created by Olije Favour on 12/1/2019.
 *Copyright (c) 2019    All rights reserved.
 */



class TaskViewModel(application: Application):AndroidViewModel(application) {


   private val taskRepository=DefaultTaskRepository.getRepository(application)

    private val _forceUpdate = MutableLiveData<Boolean>(false)


    private val _items:LiveData<Task> = _forceUpdate.switchMap { forceUpdate ->

        if(forceUpdate){

            _isDataLoading.value=true
            viewModelScope.launch {
                taskRepository.getTask(forceUpdate)
            }
            _isDataLoading.value=false

        }
          taskRepository.observeTask().switchMap {

        }


    }


    var _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading :LiveData<Boolean> = _isDataLoading

    private var _isLoadingError  = MutableLiveData<Boolean>()

    val isLoadingError = _isLoadingError




private fun filterTask(taskResult:Result<List<Task>>):LiveData<List<Task>>{

        val result =MutableLiveData<List<Task>>()



        if (taskResult is Success){
        isLoadingError.value=false


        }



  return  result
    }







}