package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.ObservableUseCase
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.mapper.Mapper
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import io.reactivex.subscribers.DisposableSubscriber

class ListTodosViewModel(var listAllTodos : ObservableUseCase<Unit, List<TodoDomain>>,
                         var response : MutableLiveData<Response>,
                         val mapper: Mapper<TodoDomain, TodoModel>,
                         val logger: Logger) : ViewModel() {

    val TAG = "ListTodosViewModel"
    val SUBSCRIBER_REQUEST_MAX_VALUE: Long = 20

    init {
        logger.v(TAG, "init")
        loadData()
    }

    override fun onCleared() {
        logger.v(TAG, "onCleared")
        listAllTodos.dispose()
    }

    fun response() : MutableLiveData<Response> {
        return response
    }

    private fun loadData(){
        listAllTodos.execute(object : DisposableSubscriber<List<TodoDomain>>() {

            override fun onComplete() {
                logger.v(TAG, "onComplete")
            }

            override fun onNext(t: List<TodoDomain>?) {
                logger.v(TAG, "onNext : " + t?.toString())
                response.value = Response.success(t?.map { todo ->
                    mapper.mapFromDomain(todo)
                })
            }

            override fun onStart() {
                logger.v(TAG, "onStart")
                response.value = Response.loading()
                request(SUBSCRIBER_REQUEST_MAX_VALUE)
            }

            override fun onError(e: Throwable) {
                logger.v(TAG, "onError")
                response.value = Response.error(e)
            }
        }, Unit)
    }
}