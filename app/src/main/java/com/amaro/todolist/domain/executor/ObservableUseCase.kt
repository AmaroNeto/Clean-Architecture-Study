package com.amaro.todolist.domain.executor

import io.reactivex.observers.DisposableSingleObserver
import org.jetbrains.annotations.NotNull

interface ObservableUseCase<P, R> {
    fun execute(@NotNull observer: DisposableSingleObserver<Any>, params: P);
    fun dispose();
}