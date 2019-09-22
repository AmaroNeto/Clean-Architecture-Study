package com.amaro.todolist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.SingleObservableUseCase
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.mapper.Mapper
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import io.reactivex.observers.DisposableSingleObserver

class NewTodoViewModel(val observable: SingleObservableUseCase<TodoDomain, Long>,
                       val response : MutableLiveData<Response>,
                       val mapper: Mapper<TodoDomain, TodoModel>,
                       val logger: Logger) : ViewModel() {
    val TAG = "NewTodoViewModel"

    override fun onCleared() {
        logger.v(TAG, "onCleared")
        observable.dispose()
    }

    fun response() : MutableLiveData<Response> {
        return response
    }

    fun create(todoModel: TodoModel){
        observable.execute(object : DisposableSingleObserver<Long>() {
            override fun onSuccess(t: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }, mapper.mapToDomain(todoModel))

    }
}