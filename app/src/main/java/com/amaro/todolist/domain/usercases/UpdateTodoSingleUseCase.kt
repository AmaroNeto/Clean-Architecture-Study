package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.extension.toResult
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import com.amaro.todolist.domain.entities.Result
import io.reactivex.Single

class UpdateTodoSingleUseCase(val todorepository: TodoRepository,
                              val log : Logger,
                              val errorHandler: ErrorHandler) :  SingleUseCase<TodoDomain, Result<Int>>() {
    val TAG = "UpdateTodoSingleUseCase"

    override fun execute(params: TodoDomain): Single<Result<Int>> {
        log.d(TAG, "execute usercase")
        return todorepository.updateTodo(params)
            .toResult(errorHandler)
    }
}