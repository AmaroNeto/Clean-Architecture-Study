package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Flowable

class ListTodosFlowableUseCase(var todorepository: TodoRepository, val log : Logger) : FlowableUseCase<Unit, List<TodoDomain>>() {
    val TAG = "ListTodosFlowableUseCase"

    override fun execute(params: Unit): Flowable<List<TodoDomain>> {
        log.d(TAG, "execute usercase")
        return todorepository.getAllTodos()
    }
}