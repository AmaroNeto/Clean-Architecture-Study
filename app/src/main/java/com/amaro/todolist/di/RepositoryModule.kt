package com.amaro.todolist.di

import com.amaro.todolist.data.local.repository.FakeTodoLocalRepository
import com.amaro.todolist.data.local.repository.TodoLocalRepository
import com.amaro.todolist.domain.repositories.TodoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TodoRepository> { TodoLocalRepository(get(), get(), get()) }

    // single<TodoRepository> { FakeTodoLocalRepository(get()) }
}