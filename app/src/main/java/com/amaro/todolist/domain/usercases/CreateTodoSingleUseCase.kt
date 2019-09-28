package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single

class CreateTodoSingleUseCase(val todorepository: TodoRepository,
                              val log : Logger) : SingleUseCase<TodoDomain, Long>() {

    override fun execute(params: TodoDomain): Single<Long> {
        return todorepository.insertTodo(params)
    }
}