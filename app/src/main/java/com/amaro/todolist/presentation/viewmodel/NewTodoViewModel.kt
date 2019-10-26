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
import com.amaro.todolist.domain.entities.Result

class NewTodoViewModel(val observable: SingleObservableUseCase<TodoDomain, Result<Long>>,
                       val response : MutableLiveData<Response>,
                       val mapper: Mapper<TodoDomain, TodoModel>,
                       val logger: Logger) : ViewModel() {
    val TAG = "NewTodoViewModel"

    override fun onCleared() {
        logger.v(TAG, "onCleared")
        observable.dispose()
    }

    private fun handleResult(result: Result<Long>) {
        when(result) {
            is Result.Success -> {
                response.value = Response.success(result.data)
            }
            is Result.Error -> {
                // TODO handle error
            }
        }
    }

    fun create(todoModel: TodoModel){
        observable.execute(object : DisposableSingleObserver<Result<Long>>() {
            override fun onStart() {
                super.onStart()
                response.value = Response.loading()
                logger.v(TAG, "onStarted")
            }

            override fun onSuccess(t: Result<Long>) {
                logger.v(TAG, "onSuccess : $t")
                handleResult(t)
            }

            override fun onError(e: Throwable) {
                logger.v(TAG, "onError")
                response.value = Response.error(e)
            }

        }, mapper.mapToDomain(todoModel))

    }
}