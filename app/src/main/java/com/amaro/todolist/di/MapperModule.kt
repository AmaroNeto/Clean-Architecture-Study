package com.amaro.todolist.di

import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.mapper.Mapper
import com.amaro.todolist.data.mapper.TodoLocalMapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.presentation.mapper.TodoModelMapper
import com.amaro.todolist.presentation.model.TodoModel
import org.koin.dsl.module

val mapperModule = module {

    single<Mapper<TodoDomain, TodoLocalEntity>> { TodoLocalMapper() }

    single<com.amaro.todolist.presentation.mapper.Mapper<TodoDomain, TodoModel>> { TodoModelMapper() }
}