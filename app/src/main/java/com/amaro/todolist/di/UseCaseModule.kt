package com.amaro.todolist.di

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.ObservableUseCase
import com.amaro.todolist.domain.executor.ObservableUseCaseImpl
import com.amaro.todolist.domain.usercases.ListTodosUserCase
import com.amaro.todolist.domain.usercases.UseCase
import org.koin.dsl.module

val useCaseModule = module {

    single<UseCase<Unit, List<TodoDomain>>> { ListTodosUserCase(get(), get()) }

    single<ObservableUseCase<Unit, List<TodoDomain>>> { ObservableUseCaseImpl(get()) }
}