package com.example.todo.tasks

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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


    init {

        loadTasks(true)

    }


   private val taskRepository=DefaultTaskRepository.getRepository(application)

    private var currentfiltering=TaskFilterType.COMPLETED_TASK

    private val _forceUpdate = MutableLiveData<Boolean>(false)


    private val _items:LiveData<List<Task>> = _forceUpdate.switchMap { forceUpdate ->

        if(forceUpdate){

            _isDataLoading.value=true
            viewModelScope.launch {
                taskRepository.getTasks(forceUpdate)
            }
            _isDataLoading.value=false

        }
          taskRepository.observeTasks().switchMap {
              filterTask(it)
        }


    }


    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     */
    fun loadTasks(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }


    var _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading :LiveData<Boolean> = _isDataLoading

    private var _isDataLoadingError  = MutableLiveData<Boolean>()

    val isDataLoadingError = _isDataLoadingError

    var itemListOfTasks:LiveData<List<Task>> = _items




private fun filterTask(taskResult:Result<List<Task>>):LiveData<List<Task>>{

        val result =MutableLiveData<List<Task>>()



        if (taskResult is Success){
        isDataLoadingError.value=false

         viewModelScope.launch {
             result.value=filterItems(taskResult.data,currentfiltering)
         }
        }



  return  result
    }


    private fun c (taskType:TaskFilterType){

        currentfiltering=taskType

        when(taskType){

            TaskFilterType.COMPLETED_TASK->{
                setFilter(R., )
            }

            TaskFilterType.ALLTASK->{

            }

            TaskFilterType.ACTIVE_TASK->{

                }

        }

    }


    private fun setFilter(@StringRes filtringLabelString:Int, @StringRes noTaskLabelString:Int,@DrawableRes noTaskIconVariable:Int,taskAddVisible:Boolean){

    }

    private fun  filterItems(itemList:List<Task>,filteringType:TaskFilterType):List<Task>{


        val result =ArrayList<Task>()

        for (item in itemList){

            when(filteringType){

                TaskFilterType.ACTIVE_TASK->{
                   if(item.isActive) result.add(item)
                }

                TaskFilterType.ALLTASK->{
                    result.add(item)
                }

                TaskFilterType.COMPLETED_TASK->{
                    if(item.isCompleted)result.add(item)
                }
            }


        }




        return result

    }







}