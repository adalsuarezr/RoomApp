package com.example.roomapp.addTasks.ui.model

data class TaskModel(val id:Long = System.currentTimeMillis(), val task:String, var selected:Boolean) {
}