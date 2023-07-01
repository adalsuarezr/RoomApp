package com.example.roomapp.addTasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomapp.addTasks.domain.AddTaskUseCase
import com.example.roomapp.addTasks.domain.DeleteTaskUseCase
import com.example.roomapp.addTasks.domain.GetTasksUseCase
import com.example.roomapp.addTasks.domain.UpdateTaskUseCase
import com.example.roomapp.addTasks.ui.TaskUIState.Error
import com.example.roomapp.addTasks.ui.TaskUIState.Loading
import com.example.roomapp.addTasks.ui.TaskUIState.Success
import com.example.roomapp.addTasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
):ViewModel() {

    val UIState: StateFlow<TaskUIState> = getTasksUseCase().map(::Success)
        .catch{ Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),  //StateIn convierte Flow en StateFlow
            Loading
        )

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

    //private val _tasks = mutableStateListOf<TaskModel>()
    //val tasks: List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value=false
    }

    fun onTaskCreated(task: String) {
        onDialogClose()
        //_tasks.add(TaskModel(task = task))

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialogClicked() {
        _showDialog.value=true
    }

    fun onCheckBoxSelected(task: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(selected = !task.selected))
        }
        /*val index = _tasks.indexOf(task)
        //Si solo cambiamos el valor de selected, la vista no se recompone
        _tasks[index] = _tasks[index].let { it.copy(selected=!it.selected) }*/

    }

    fun onItemRemove(task: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
        /*val task =_tasks.find{it.id == taskModel.id}
        _tasks.remove(task)*/
    }

}