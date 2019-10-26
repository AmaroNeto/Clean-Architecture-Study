package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.executor.FlowableObservableUseCase
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.view.Response
import io.reactivex.subscribers.DisposableSubscriber
import com.amaro.todolist.domain.entities.Result

class MainViewModel(
    val countTodos: FlowableObservableUseCase<Unit,Result<Int>>,
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

    private fun handleResult(result: Result<Int>) {
        when(result) {
            is Result.Success -> {
                response.value = Response.success(result.data)
            }
            is Result.Error -> {
                // TODO handle error
            }
        }
    }

    private fun loadData(){
        countTodos.execute(object : DisposableSubscriber<Result<Int>>() {
            override fun onStart() {
                logger.v(TAG, "onStart")
                request(SUBSCRIBER_REQUEST_MAX_VALUE)
            }

            override fun onComplete() {
                logger.v(TAG, "onComplete")
            }

            override fun onNext(t: Result<Int>) {
                logger.v(TAG, "onNext : $t")
                handleResult(t)
            }

            override fun onError(t: Throwable) {
                logger.v(TAG, "onError")
                response.value = Response.error(t)
            }

        }, Unit)
    }

}