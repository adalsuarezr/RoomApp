package com.example.roomapp.addTasks.ui.model

data class TaskModel(val id:Int = System.currentTimeMillis().hashCode(), val task:String, var selected:Boolean=false) {
}