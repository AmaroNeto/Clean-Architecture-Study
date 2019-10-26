package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.Result
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.extension.toResult
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Flowable

class ListTodosFlowableUseCase(var todorepository: TodoRepository,
                               val log : Logger,
                               val errorHandler: ErrorHandler) : FlowableUseCase<Unit, Result<List<TodoDomain>>>() {
    val TAG = "ListTodosFlowableUseCase"

    override fun execute(params: Unit): Flowable<Result<List<TodoDomain>>> {
        log.d(TAG, "execute usercase")
        return todorepository
            .getAllTodos()
            .toResult(errorHandler)
    }
}