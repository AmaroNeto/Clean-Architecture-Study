package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Flowable

class CountTodosFlowableUseCase(var todorepository: TodoRepository, val log : Logger) : FlowableUseCase<Unit, Int>() {
    override fun execute(params: Unit): Flowable<Int> {
        return todorepository.countTodos()
    }
}