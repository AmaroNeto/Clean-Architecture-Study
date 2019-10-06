package com.amaro.todolist.di

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.FlowableObservableUseCase
import com.amaro.todolist.domain.executor.FlowableObservableUseCaseImpl
import com.amaro.todolist.domain.executor.SingleObservableUseCase
import com.amaro.todolist.domain.executor.SingleObservableUseCaseImpl
import com.amaro.todolist.domain.usercases.*
import org.koin.dsl.module

val useCaseModule = module {

    single<FlowableUseCase<Unit, List<TodoDomain>>> { ListTodosFlowableUseCase(get(), get()) }

    // single<FlowableUseCase<Unit, Int>> { CountTodosFlowableUseCase(get(), get()) }

    single<FlowableObservableUseCase<Unit, List<TodoDomain>>> { FlowableObservableUseCaseImpl(get()) }

    single<SingleUseCase<TodoDomain, Long>> { CreateTodoSingleUseCase(get(), get()) }

    factory <SingleObservableUseCase<TodoDomain, Long>> { SingleObservableUseCaseImpl(get()) }
}