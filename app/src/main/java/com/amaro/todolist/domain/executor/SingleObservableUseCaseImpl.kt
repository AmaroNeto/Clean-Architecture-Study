package com.amaro.todolist.domain.executor

import com.amaro.todolist.domain.usercases.SingleUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SingleObservableUseCaseImpl<P, R>(val useCase: SingleUseCase<P, R>) : SingleObservableUseCase<P, R> {
    val disposables = CompositeDisposable()

    override fun execute(observer: DisposableSingleObserver<R>, params: P) {
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