package com.amaro.todolist.di

import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import com.amaro.todolist.presentation.viewmodel.NewTodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListTodosViewModel(get(named("ListTodosFlowableObservableUseCase")), get(), get(), get()) }
    viewModel { NewTodoViewModel(get(named("CreateTodoObservableUseCase")), get(), get(), get()) }
}