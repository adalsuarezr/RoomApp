package com.example.roomapp.addTasks.data

import com.example.roomapp.addTasks.ui.model.TaskModel
import javax.inject.Inject
import javax.inject.Singleton

//No usamos una interfaz por simplicidad ya que esta en un único módulo
@Singleton
class TaskRepository @Inject constructor(/*private val taskdao: TaskDao*/){
    suspend fun add(task: TaskModel){

    }
}