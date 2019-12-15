package com.example.todo.tasks

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.example.todo.data.Result
import com.example.todo.data.Task
import com.example.todo.data.source.DefaultTaskRepository
import kotlinx.coroutines.launch
import com.example.todo.data.Result.Success
import com.example.todo.R


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

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel

    private val _noTasksLabel = MutableLiveData<Int>()
    val noTasksLabel: LiveData<Int> = _noTasksLabel

    private val _noTaskIconRes = MutableLiveData<Int>()
    val noTaskIconRes: LiveData<Int> = _noTaskIconRes

    private val _tasksAddViewVisible = MutableLiveData<Boolean>()
    val tasksAddViewVisible: LiveData<Boolean> = _tasksAddViewVisible



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


    private fun startFiltering (taskType:TaskFilterType){

        currentfiltering=taskType

        when(taskType){

            TaskFilterType.COMPLETED_TASK->{
                setFilter(R.string.label_all,R.string.)
            }

            TaskFilterType.ALLTASK->{

            }

            TaskFilterType.ACTIVE_TASK->{

                }

        }

    }


    private fun setFilter(@StringRes filtringLabelString:Int, @StringRes noTaskLabelString:Int,noTaskIconVariable:Int,taskAddVisible:Boolean){

    }





//    /**
//     * Sets the current task filtering type.
//     *
//     * @param requestType Can be [TasksFilterType.ALL_TASKS],
//     * [TasksFilterType.COMPLETED_TASKS], or
//     * [TasksFilterType.ACTIVE_TASKS]
//     */
//    fun setFiltering(requestType: TasksFilterType) {
//        currentFiltering = requestType
//
//        // Depending on the filter type, set the filtering label, icon drawables, etc.
//        when (requestType) {
//            TaskFilterType.ALLTASK -> {
//                setFilter(
//                    R.string.label_all, R.string.no_tasks_all,
//                    R.drawable.logo_no_fill, true
//                )
//            }
//            TaskFilterType.ACTIVE_TASK -> {
//                setFilter(
//                    R.string.label_active, R.string.no_tasks_active,
//                    R.drawable.ic_check_circle_96dp, false
//                )
//            }
//            TaskFilterType.COMPLETED_TASK -> {
//                setFilter(
//                    R.string.label_completed, R.string.no_tasks_completed,
//                    R.drawable.ic_verified_user_96dp, false
//                )
//            }
//        }
//        // Refresh list
//        loadTasks(false)
//    }
//
//    private fun setFilter(
//        @StringRes filteringLabelString: Int, @StringRes noTasksLabelString: Int,
//        @DrawableRes noTaskIconDrawable: Int, tasksAddVisible: Boolean
//    ) {
//        _currentFilteringLabel.value = filteringLabelString
//        _noTasksLabel.value = noTasksLabelString
//        _noTaskIconRes.value = noTaskIconDrawable
//        _tasksAddViewVisible.value = tasksAddVisible
//    }



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