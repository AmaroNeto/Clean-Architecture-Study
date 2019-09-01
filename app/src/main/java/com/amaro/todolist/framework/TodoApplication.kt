package com.amaro.todolist.framework

import android.app.Application
import com.amaro.todolist.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TodoApplication)
            androidFileProperties()
            modules(listOf(
                repositoryModule,
                useCaseModule,
                presentationModule,
                mapperModule,
                daoModule,
                viewModelModule
            ))
        }
    }
}