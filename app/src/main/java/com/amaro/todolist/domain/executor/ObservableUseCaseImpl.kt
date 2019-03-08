package com.amaro.todolist.domain.executor

import com.amaro.todolist.domain.usercases.UseCase
import com.amaro.todolist.presentation.model.TodoModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import org.jetbrains.annotations.NotNull
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class ObservableUseCaseImpl<P, R>(@NotNull var useCase: UseCase<P, R>) : ObservableUseCase<P, R> {

    val disposables = CompositeDisposable()

    override fun execute(observer: DisposableSingleObserver<List<TodoModel>>, params: P) {

        val single = useCase.execute(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        addDisposable(single.subscribeWith(observer))
    }

    override fun dispose() {
        if(!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}