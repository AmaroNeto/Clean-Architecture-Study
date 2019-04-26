package com.amaro.todolist.presentation.model

import java.io.Serializable

class TodoModel(var title: String = "",
                var done: Boolean = false) : Serializable{
    var id : Int = 0
    var description: String = ""
}