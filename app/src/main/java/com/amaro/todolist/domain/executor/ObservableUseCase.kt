package com.amaro.todolist.domain.executor

import com.amaro.todolist.presentation.model.TodoModel
import io.reactivex.observers.DisposableSingleObserver
import org.jetbrains.annotations.NotNull

interface ObservableUseCase<P, R> {
    fun execute(@NotNull observer: DisposableSingleObserver<R>, params: P) : R
    fun dispose()
}