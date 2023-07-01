package com.example.roomapp.addTasks.ui

import com.example.roomapp.addTasks.ui.model.TaskModel

sealed interface TaskUIState{
    object Loading:TaskUIState
    data class Error(val throwable: Throwable):TaskUIState
    data class Success(val tasks: List<TaskModel>):TaskUIState
}