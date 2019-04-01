package com.amaro.todolist.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.amaro.todolist.R
import com.amaro.todolist.data.local.AppDataBase
import com.amaro.todolist.data.local.dao.TodoDao
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.local.repository.TodoLocalRepository
import com.amaro.todolist.data.mapper.TodoLocalMapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.ObservableUseCaseImpl
import com.amaro.todolist.domain.repositories.TodoRepository
import com.amaro.todolist.domain.usercases.ListTodosUserCase
import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import com.amaro.todolist.presentation.viewmodel.ViewModelFactory
import com.amaro.todolist.presentation.mapper.Mapper
import com.amaro.todolist.presentation.mapper.TodoModelMapper
import com.amaro.todolist.presentation.model.TodoModel


class ListTodoActivity : AppCompatActivity() {

    val vm : ListTodosViewModel by lazy {

        val db : AppDataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "todo-db"
        ).build()
        val todoDao : TodoDao = db.todoDao()

        val mapperRepository: TodoLocalMapper = TodoLocalMapper()
        val todoRepository : TodoLocalRepository = TodoLocalRepository(
            todoDao,
            mapperRepository
        )

        val response : MutableLiveData<Response> = MutableLiveData<Response>()
        val mapper : TodoModelMapper = TodoModelMapper()
        val observableUserCase : ObservableUseCaseImpl<Unit,List<TodoDomain>> = ObservableUseCaseImpl(ListTodosUserCase(todoRepository))

        ViewModelProviders.of(this, ViewModelFactory<ListTodosViewModel>{ ListTodosViewModel(
                observableUserCase,response, mapper)
        }).get(ListTodosViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_todo_activity)

    }
}