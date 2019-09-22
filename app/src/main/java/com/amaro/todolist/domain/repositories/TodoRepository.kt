package com.amaro.todolist.domain.repositories

import com.amaro.todolist.domain.entities.TodoDomain
import io.reactivex.Flowable

interface TodoRepository {
    fun getAllTodos() : Flowable<List<TodoDomain>>
    fun insertTodo(todoDomain: TodoDomain): Long
}