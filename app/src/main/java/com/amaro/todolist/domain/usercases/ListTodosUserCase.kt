package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single

class ListTodosUserCase(var todorepository: TodoRepository) : UseCase<Unit, List<TodoDomain>>() {
    override fun execute(params: Unit): Single<List<TodoDomain>> {
        return todorepository.getAllTodos()
    }
}