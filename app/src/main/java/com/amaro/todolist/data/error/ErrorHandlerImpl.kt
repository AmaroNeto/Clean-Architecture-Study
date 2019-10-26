package com.amaro.todolist.data.error

import com.amaro.todolist.domain.entities.ErrorEntity
import com.amaro.todolist.domain.error.ErrorHandler
import java.io.IOException

class ErrorHandlerImpl: ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable) {
            is IOException -> ErrorEntity.NetWork(throwable)
            else -> ErrorEntity.Unknown(throwable)
        }
    }
}