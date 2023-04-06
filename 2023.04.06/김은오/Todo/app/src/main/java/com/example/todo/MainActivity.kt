package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.ui.add_edit_todo.AddEditTodoScreen
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.todolist.TodoListScreen
import com.example.todo.util.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                TodoScreen()
            }
        }
    }
}

@Composable
fun TodoScreen(){

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.LightGray,
            darkIcons = false
        )
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.TODO_LIST){
        composable(Routes.TODO_LIST){
            TodoListScreen(onNavigate = { navController.navigate(it.route) })
        }
        composable(
            route = Routes.ADD_EDIT_TODO + "?todoID={todoId}",
            arguments = listOf(
                navArgument(name = "todoId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditTodoScreen(onPopStack = { navController.popBackStack() })
        }
    }
}