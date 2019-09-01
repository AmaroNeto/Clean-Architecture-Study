package com.amaro.todolist.domain.repositories

import com.amaro.todolist.domain.entities.TodoDomain
import io.reactivex.Flowable
import io.reactivex.Single

interface TodoRepository {
    fun getAllTodos() : Flowable<List<TodoDomain>>
}