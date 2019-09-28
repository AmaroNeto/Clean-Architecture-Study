package com.amaro.todolist.data.local.repository

import com.amaro.todolist.data.local.dao.TodoDao
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.mapper.Mapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Flowable
import io.reactivex.Single

class TodoLocalRepository(val todoDao: TodoDao,
                          val mapper: Mapper<TodoDomain, TodoLocalEntity>,
                          val log : Logger): TodoRepository {

    val TAG = "TodoLocalRepository"

    override fun getAllTodos(): Flowable<List<TodoDomain>> {
        log.d(TAG, "getAllTodos")
        return todoDao.getAllTodos().map {
            it.map { todo ->
                mapper.mapToDomain(todo)
            }
        }
    }

    override fun insertTodo(todoDomain: TodoDomain): Single<Long> {
        log.v(TAG, "new Todo inserted: {${todoDomain.title}}")
        return Single.fromCallable {todoDao.insertTodo(mapper.mapFromDomain(todoDomain))}
    }
}