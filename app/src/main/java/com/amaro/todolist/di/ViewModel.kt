package com.amaro.todolist.di

import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import com.amaro.todolist.presentation.viewmodel.MainViewModel
import com.amaro.todolist.presentation.viewmodel.NewTodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListTodosViewModel(get(named("ListTodosFlowableObservableUseCase")),
        get(named("UpdateTodoObservableUseCase")), get(), get(), get(), get()) }
    viewModel { NewTodoViewModel(get(named("CreateTodoObservableUseCase")), get(), get(), get()) }
    viewModel { MainViewModel(get(named("CountTodosFlowableObservableUseCase")), get(), get()) }
}