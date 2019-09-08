package com.amaro.todolist.di

import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListTodosViewModel(get(), get(), get(), get()) }
}