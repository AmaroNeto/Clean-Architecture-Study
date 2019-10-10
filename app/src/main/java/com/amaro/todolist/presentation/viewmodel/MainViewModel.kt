package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.executor.FlowableObservableUseCase
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.view.Response
import io.reactivex.subscribers.DisposableSubscriber

class MainViewModel(
    val countTodos: FlowableObservableUseCase<Unit,Long>,
    val response : MutableLiveData<Response>,
    val logger: Logger) : ViewModel() {

    val TAG = "MainViewModel"
    val SUBSCRIBER_REQUEST_MAX_VALUE: Long = 20

    init {
        logger.v(TAG, "init")
        loadData()
    }

    override fun onCleared() {
        logger.v(TAG, "onCleared")
        countTodos.dispose()
    }

    fun response() : MutableLiveData<Response> {
        return response
    }

    private fun loadData(){
        countTodos.execute(object : DisposableSubscriber<Long>() {
            override fun onStart() {
                logger.v(TAG, "onStart")
                request(SUBSCRIBER_REQUEST_MAX_VALUE)
            }

            override fun onComplete() {
                logger.v(TAG, "onComplete")
            }

            override fun onNext(t: Long) {
                logger.v(TAG, "onNext : $t")
                response.value = Response.success(t)
            }

            override fun onError(t: Throwable) {
                logger.v(TAG, "onError")
                response.value = Response.error(t)
            }

        }, Unit)
    }

}