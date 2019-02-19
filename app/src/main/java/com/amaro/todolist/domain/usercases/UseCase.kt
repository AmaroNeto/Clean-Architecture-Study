package com.amaro.todolist.domain.usercases

import android.R
import io.reactivex.Single


abstract class UseCase<P,R> {
    abstract fun execute(params: P): Single<R>
}