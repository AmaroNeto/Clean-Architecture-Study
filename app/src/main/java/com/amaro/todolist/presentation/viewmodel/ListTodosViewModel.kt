package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.executor.ObservableUseCase
import com.amaro.todolist.presentation.model.TodoModel
import io.reactivex.observers.DisposableSingleObserver



class ListTodosViewModel(var listAllTodos : ObservableUseCase<Unit, List<TodoModel>>) : ViewModel() {

    private fun loadData(){
        listAllTodos.execute(object : DisposableSingleObserver<List<TodoModel>>() {
            override fun onSuccess(t: List<TodoModel>) {
                t.map {  }
            }

            override fun onError(e: Throwable) {

            }
        }, Unit)
    }
}