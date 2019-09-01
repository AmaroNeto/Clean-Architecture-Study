package com.amaro.todolist.data.local.repository

import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.mapper.Mapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Flowable
import io.reactivex.Single

class FakeTodoLocalRepository(val mapper: Mapper<TodoDomain, TodoLocalEntity>) : TodoRepository {
    override fun getAllTodos(): Flowable<List<TodoDomain>> {
        return createFlowable().map {
            it.map { todo ->
                mapper.mapToDomain(todo)
            }
        }
    }

    private fun createFlowable() : Flowable<List<TodoLocalEntity>> {
        return Flowable.just(getDemoTodoLocalEntity())
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