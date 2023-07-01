package com.example.roomapp.addTasks.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.roomapp.addTasks.ui.model.TaskModel

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun TasksScreen(taskViewModel: TaskViewModel) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)
    val uiState by produceState<TaskUIState>(
        initialValue = TaskUIState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            taskViewModel.UIState.collect { value = it }
        }
    }

    when(uiState){
        is TaskUIState.Error -> {
            TODO()
        }

        TaskUIState.Loading -> {
            CircularProgressIndicator()
        }

        is TaskUIState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTasksDialog(
                    showDialog,
                    onDismiss = { taskViewModel.onDialogClose() },
                    onTaskAdded = { taskViewModel.onTaskCreated(it) })
                FabDialog(Modifier.align(Alignment.BottomEnd), taskViewModel)
                TasksList((uiState as TaskUIState.Success).tasks, taskViewModel)
            }
        }
    }
}


@Composable
fun TasksList(tasks: List<TaskModel>, taskViewModel: TaskViewModel) {
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            ItemTask(task, taskViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
        //Para que al pulsar el item se borre, lo he sustituido por un botón
        /*.pointerInput(Unit) {
            detectTapGestures(onLongPress = { taskViewModel.onItemRemove(taskModel) })
        }*/,
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
            IconButton(
                onClick = { taskViewModel.onItemRemove(taskModel) }
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "")
            }
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(taskModel) })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = {
            taskViewModel.onShowDialogClicked()
        },
        modifier = modifier.padding(20.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask: String by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = myTask,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = { myTask = it })
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        onTaskAdded(myTask)
                        myTask = ""
                    }, modifier = Modifier.fillMaxWidth(),
                    enabled = myTask.isNotEmpty()
                ) {
                    Text(text = "Añadir tarea")
                }

            }
        }
    }
}