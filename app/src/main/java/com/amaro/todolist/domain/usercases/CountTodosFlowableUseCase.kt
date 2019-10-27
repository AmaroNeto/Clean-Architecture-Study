package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import com.amaro.todolist.domain.entities.Result
import com.amaro.todolist.domain.extension.toResult
import io.reactivex.Flowable

class CountTodosFlowableUseCase(var todorepository: TodoRepository,
                                val log : Logger,
                                val errorHandler: ErrorHandler) : FlowableUseCase<Unit, Result<Int>>() {

    val TAG = "CountTodosFlowableUseCase"

    override fun execute(params: Unit): Flowable<Result<Int>> {
        log.d(TAG, "execute usercase")
        return todorepository.countTodos()
            .toResult(errorHandler)
    }
}