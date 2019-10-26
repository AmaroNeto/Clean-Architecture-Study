package com.amaro.todolist.di

import androidx.lifecycle.MutableLiveData
import com.amaro.todolist.data.error.ErrorHandlerImpl
import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.logger.AppLog
import com.amaro.todolist.presentation.view.Response
import org.koin.dsl.module

val presentationModule = module {
    single<Logger> { AppLog() }

    single<ErrorHandler> { ErrorHandlerImpl() }

    factory { MutableLiveData<Response>() }
}