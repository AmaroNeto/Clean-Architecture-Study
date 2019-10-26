package com.amaro.todolist.domain.extension

import com.amaro.todolist.domain.entities.Result
import com.amaro.todolist.domain.error.ErrorHandler
import io.reactivex.Flowable
import io.reactivex.Single

fun <T> Flowable<T>.toResult(errorHandler: ErrorHandler): Flowable<Result<T>> =
    this.map {
        Result.Success(it) as Result<T>
    }.onErrorReturn {
        Result.Error(errorHandler.getError(it))
    }

fun <T> Single<T>.toResult(errorHandler: ErrorHandler): Single<Result<T>> =
    this.map {
        Result.Success(it) as Result<T>
    }.onErrorReturn {
        Result.Error(errorHandler.getError(it))
    }
