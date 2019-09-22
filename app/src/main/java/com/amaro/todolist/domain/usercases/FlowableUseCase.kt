package com.amaro.todolist.domain.usercases

import io.reactivex.Flowable

abstract class FlowableUseCase<P,R> {
    abstract fun execute(params: P): Flowable<R>
}