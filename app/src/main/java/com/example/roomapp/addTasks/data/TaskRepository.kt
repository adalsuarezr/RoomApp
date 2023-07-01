package com.example.roomapp.addTasks.data

import com.example.roomapp.addTasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

//No usamos una interfaz por simplicidad ya que esta en un único módulo
@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map {
                items -> items.map {
            TaskModel(it.id, it.task, it.selected) }
        }

    suspend fun add(task: TaskModel) {
        taskDao.addTask(task.toEntity())
    }

    suspend fun update(task: TaskModel) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun delete(task: TaskModel) {
        taskDao.deleteTask(task.toEntity())

    }
}

fun TaskModel.toEntity():TaskEntity{
    return TaskEntity(this.id, this.task, this.selected)
}