package com.amaro.todolist.presentation.model

import com.amaro.todolist.R
import com.amaro.todolist.presentation.view.adapter.AdapterModel
import java.io.Serializable

class TodoModel(var title: String = "",
                var done: Boolean = false) : Serializable, AdapterModel{
    var id : Long = 0
    var description: String = ""

    override fun layoutId(): Int {
        return R.layout.todo_list_item
    }

    override fun isFilterable(filter: String): Boolean {
        return filter == title
    }

}