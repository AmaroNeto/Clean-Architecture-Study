package com.amaro.todolist.data.local.repository

import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.mapper.Mapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single

class FakeTodoLocalRepository(val mapper: Mapper<TodoDomain, TodoLocalEntity>) : TodoRepository {
    override fun getAllTodos(): Single<List<TodoDomain>> {
        return createSingle().map {
            it.map { todo ->
                mapper.mapToDomain(todo)
            }
        }
    }

    private fun createSingle() : Single<List<TodoLocalEntity>> {
        return Single.create { emitter ->
            val list = getDemoTodoLocalEntity()
            emitter.onSuccess(list)
        }
    }

    private fun getDemoTodoLocalEntity() : List<TodoLocalEntity> {
        val list : MutableList<TodoLocalEntity> = mutableListOf()
        for(i in 1..6) {
            var todo : TodoLocalEntity = TodoLocalEntity()
            todo.id = i
            todo.title = "Test "+i
            todo.description = "description"

            list.add(todo)
        }

        return list
    }
}