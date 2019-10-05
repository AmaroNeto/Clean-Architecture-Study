package com.amaro.todolist.presentation.view.adapter

interface AdapterModel {
    fun layoutId(): Int
    fun isFilterable(filter: String): Boolean
}