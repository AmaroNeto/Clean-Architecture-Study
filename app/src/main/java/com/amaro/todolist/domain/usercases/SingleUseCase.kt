package com.amaro.todolist.domain.usercases

import io.reactivex.Single

abstract class SingleUseCase<P,R> {
    abstract fun execute(params: P): Single<R>
}