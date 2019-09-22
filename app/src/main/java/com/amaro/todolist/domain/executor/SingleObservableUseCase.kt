package com.amaro.todolist.domain.executor

import io.reactivex.observers.DisposableSingleObserver
import org.jetbrains.annotations.NotNull

interface SingleObservableUseCase<P, R> {
    fun execute(@NotNull observer: DisposableSingleObserver<R>, params: P)
    fun dispose()
}