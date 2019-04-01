package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.ObservableUseCase
import com.amaro.todolist.presentation.mapper.Mapper
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import io.reactivex.observers.DisposableSingleObserver

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
        listAllTodos.execute(object : DisposableSingleObserver<List<TodoDomain>>() {

            override fun onStart() {
                response.value = Response.loading()
            }

            override fun onSuccess(t: List<TodoDomain>) {
                t.map { todo ->
                    mapper.mapFromDomain(todo)
                }
                response.value = Response.success(t)
            }

            override fun onError(e: Throwable) {
                response.value = Response.error(e)
            }
        }, Unit)
    }
}