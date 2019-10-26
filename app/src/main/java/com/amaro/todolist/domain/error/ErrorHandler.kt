package com.amaro.todolist.domain.error

import com.amaro.todolist.domain.entities.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}