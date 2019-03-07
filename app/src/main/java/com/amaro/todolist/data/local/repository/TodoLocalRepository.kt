package com.amaro.todolist.data.local.repository

import com.amaro.todolist.data.local.dao.TodoDao
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.mapper.Mapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single

class TodoLocalRepository(val todoDao: TodoDao, val mapper: Mapper<TodoDomain, TodoLocalEntity>): TodoRepository {

    override fun getAllTodos(): Single<List<TodoDomain>> {
        return todoDao.getAllTodos().map {
            it.map { todo ->
                mapper.mapToDomain(todo)
            }
        }
    }
}