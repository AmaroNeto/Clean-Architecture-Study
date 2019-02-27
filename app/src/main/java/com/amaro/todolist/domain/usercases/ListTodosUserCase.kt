package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single

class ListTodosUserCase(var todorepository: TodoRepository) : UseCase<TodoDomain, Void>() {
    override fun execute(params: TodoDomain): Single<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}