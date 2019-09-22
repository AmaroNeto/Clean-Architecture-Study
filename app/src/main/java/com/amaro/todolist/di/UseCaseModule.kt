package com.amaro.todolist.di

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.FlowableObservableUseCase
import com.amaro.todolist.domain.executor.FlowableObservableUseCaseImpl
import com.amaro.todolist.domain.usercases.ListTodosFlowableUseCase
import com.amaro.todolist.domain.usercases.FlowableUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single<FlowableUseCase<Unit, List<TodoDomain>>> { ListTodosFlowableUseCase(get(), get()) }

    single<FlowableObservableUseCase<Unit, List<TodoDomain>>> { FlowableObservableUseCaseImpl(get()) }
}