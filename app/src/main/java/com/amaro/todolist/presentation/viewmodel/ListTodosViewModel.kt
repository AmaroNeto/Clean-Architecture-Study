package com.amaro.todolist.presentation.viewmodel

import com.amaro.todolist.domain.entities.Result
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.FlowableObservableUseCase
import com.amaro.todolist.domain.executor.SingleObservableUseCase
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.mapper.Mapper
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.DisposableSubscriber

class ListTodosViewModel(val listAllTodos : FlowableObservableUseCase<Unit, Result<List<TodoDomain>>>,
                         val updateTodos: SingleObservableUseCase<TodoDomain, Result<Int>>,
                         val loadDataResponse : MutableLiveData<Response>,
                         val updateTodoResponse : MutableLiveData<Response>,
                         val mapper: Mapper<TodoDomain, TodoModel>,
                         val logger: Logger) : ViewModel() {

    val TAG = "ListTodosViewModel"
    val SUBSCRIBER_REQUEST_MAX_VALUE: Long = 20

    override fun onCleared() {
        logger.v(TAG, "onCleared")
        listAllTodos.dispose()
        updateTodos.dispose()
    }

    fun updateTodo(todoModel: TodoModel) {
        logger.v(TAG, "update TodoModel ${todoModel.id}")
        updateTodos.execute(object : DisposableSingleObserver<Result<Int>>() {
            override fun onSuccess(t: Result<Int>) {
                logger.i(TAG, "onSuccess")
                handleUpdateTodoResult(t)
            }

            override fun onError(e: Throwable) {
                logger.e(TAG, "onError: ${e.localizedMessage}")
            }

        }, mapper.mapToDomain(todoModel))
    }

    fun loadData(){
        listAllTodos.execute(object : DisposableSubscriber<Result<List<TodoDomain>>>() {

            override fun onComplete() {
                logger.v(TAG, "onComplete")
            }

            override fun onNext(t: Result<List<TodoDomain>>?) {
                logger.v(TAG, "onNext : " + t?.toString())
                handleLoadDataResult(t)
            }

            override fun onStart() {
                logger.v(TAG, "onStart")
                loadDataResponse.value = Response.loading()
                request(SUBSCRIBER_REQUEST_MAX_VALUE)
            }

            override fun onError(e: Throwable) {
                logger.e(TAG, "onError")
                loadDataResponse.value = Response.error(e)
            }
        }, Unit)
    }

    private fun handleLoadDataResult(result: Result<List<TodoDomain>>?) {
        when(result) {
            is Result.Success -> {
                loadDataResponse.value = Response.success(result.data?.map { todo ->
                    mapper.mapFromDomain(todo)
                })
            }
            is Result.Error -> {
                // TODO handle error
            }
        }
    }

    private fun handleUpdateTodoResult(result: Result<Int>?) {
        when(result) {
            is Result.Success -> updateTodoResponse.value = Response.success(result.data)
            is Result.Error -> {
                // TODO handle error
            }
        }
    }
}