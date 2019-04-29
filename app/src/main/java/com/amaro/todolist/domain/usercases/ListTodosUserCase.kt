package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single

class ListTodosUserCase(var todorepository: TodoRepository, val log : Logger) : UseCase<Unit, List<TodoDomain>>() {
    val TAG = "ListTodosUserCase"

    override fun execute(params: Unit): Single<List<TodoDomain>> {
        log.d(TAG, "execute usercase")
        return todorepository.getAllTodos()
    }
}