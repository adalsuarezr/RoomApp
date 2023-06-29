package com.example.roomapp.addTasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomapp.addTasks.ui.model.TaskModel
import javax.inject.Inject

class TaskViewModel @Inject constructor():ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value=false
    }

    fun onTaskCreated(task: String) {
        onDialogClose()
        _tasks.add(TaskModel(task = task, selected = false))
    }

    fun onShowDialogClicked() {
        _showDialog.value=true
    }

    fun onCheckBoxSelected(task: TaskModel) {
        val index = _tasks.indexOf(task)
        //Si solo cambiamos el valor de selected, la vista no se recompone
        _tasks[index] = _tasks[index].let { it.copy(selected=!it.selected) }}

    fun onItemRemove(taskModel: TaskModel) {
        val task =_tasks.find{it.id == taskModel.id}
        _tasks.remove(task)
    }

}