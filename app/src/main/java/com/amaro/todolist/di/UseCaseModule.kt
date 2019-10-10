package com.amaro.todolist.di

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.FlowableObservableUseCase
import com.amaro.todolist.domain.executor.FlowableObservableUseCaseImpl
import com.amaro.todolist.domain.executor.SingleObservableUseCase
import com.amaro.todolist.domain.executor.SingleObservableUseCaseImpl
import com.amaro.todolist.domain.usercases.*
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {

    single<FlowableUseCase<Unit, List<TodoDomain>>>(named("ListTodosFlowableUseCase")) { ListTodosFlowableUseCase(get(), get()) }

    single<FlowableUseCase<Unit, Int>>(named("CountTodosFlowableUseCase")) { CountTodosFlowableUseCase(get(), get()) } bind CountTodosFlowableUseCase::class

    single<SingleUseCase<TodoDomain, Long>>(named("CreateTodoSingleUseCase")) { CreateTodoSingleUseCase(get(), get()) }

    factory<FlowableObservableUseCase<Unit, List<TodoDomain>>>(named("ListTodosFlowableObservableUseCase")) { FlowableObservableUseCaseImpl(get(named("ListTodosFlowableUseCase"))) }

    factory<FlowableObservableUseCase<Unit, Long>>(named("CountTodosFlowableObservableUseCase")) { FlowableObservableUseCaseImpl(get(named("CountTodosFlowableUseCase"))) }

    factory<SingleObservableUseCase<TodoDomain, Long>>(named("CreateTodoObservableUseCase")) { SingleObservableUseCaseImpl(get(named("CreateTodoSingleUseCase"))) }
}