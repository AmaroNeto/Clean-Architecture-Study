package com.amaro.todolist.di

import androidx.room.Room
import com.amaro.todolist.data.local.AppDataBase
import org.koin.dsl.module

val daoModule = module {
    single { Room.databaseBuilder(
        get(),
        AppDataBase::class.java, "todo-db").build() }

    single {get<AppDataBase>().todoDao()}
}