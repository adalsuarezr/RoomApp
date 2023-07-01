package com.example.roomapp.addTasks.domain

import com.example.roomapp.addTasks.data.TaskRepository
import com.example.roomapp.addTasks.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.add(taskModel)
    }
}