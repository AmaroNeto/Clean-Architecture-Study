package com.amaro.todolist.domain.executor

import io.reactivex.subscribers.DisposableSubscriber
import org.jetbrains.annotations.NotNull

interface ObservableUseCase<P, R> {
    fun execute(@NotNull observer: DisposableSubscriber<R>, params: P)
    fun dispose()
}