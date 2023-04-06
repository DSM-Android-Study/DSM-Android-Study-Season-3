package com.example.todo.ui.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    data class OnDateChange(val date: String): AddEditTodoEvent()
    object OnSaveTodoClick: AddEditTodoEvent()
}