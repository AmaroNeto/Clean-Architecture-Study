package com.amaro.todolist.domain.usercases

import android.R
import io.reactivex.Flowable

abstract class UseCase<P,R> {
    abstract fun execute(params: P): Flowable<R>
}