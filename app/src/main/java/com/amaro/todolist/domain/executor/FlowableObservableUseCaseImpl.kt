package com.amaro.todolist.domain.executor

import com.amaro.todolist.domain.usercases.FlowableUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber

class FlowableObservableUseCaseImpl<P, R>(val useCase: FlowableUseCase<P, R>) : FlowableObservableUseCase<P, R> {

    val disposables = CompositeDisposable()

    override fun execute(observer: DisposableSubscriber<R>, params: P) {

        addDisposable(useCase.execute(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer))
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