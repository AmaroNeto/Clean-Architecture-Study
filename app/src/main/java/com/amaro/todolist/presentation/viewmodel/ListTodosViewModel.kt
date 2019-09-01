package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.ObservableUseCase
import com.amaro.todolist.presentation.mapper.Mapper
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.DisposableSubscriber

class ListTodosViewModel(var listAllTodos : ObservableUseCase<Unit, List<TodoDomain>>,
                         var response : MutableLiveData<Response>,
                         val mapper: Mapper<TodoDomain, TodoModel>) : ViewModel() {

    init {
        loadData()
    }

    override fun onCleared() {
        listAllTodos.dispose()
    }

    fun response() : MutableLiveData<Response> {
        return response
    }

    private fun loadData(){
        listAllTodos.execute(object : DisposableSubscriber<List<TodoDomain>>() {
            override fun onComplete() {
            }

            override fun onNext(t: List<TodoDomain>?) {
                response.value = Response.success(t?.map { todo ->
                    mapper.mapFromDomain(todo)
                })            }

            override fun onStart() {
                response.value = Response.loading()
            }

            override fun onError(e: Throwable) {
                response.value = Response.error(e)
            }
        }, Unit)
    }
}