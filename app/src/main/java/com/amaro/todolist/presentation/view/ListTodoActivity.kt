package com.amaro.todolist.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.amaro.todolist.R
import com.amaro.todolist.data.local.AppDataBase
import com.amaro.todolist.data.local.dao.TodoDao
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.local.repository.FakeTodoLocalRepository
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
import com.amaro.todolist.presentation.view.adapter.TodoListAdapter
import kotlinx.android.synthetic.main.list_todo_activity.*


class ListTodoActivity : AppCompatActivity(), TodoListAdapter.TodoListAdapterCallback {

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

        //Fake data ------------
        val fakeTodoRepository : FakeTodoLocalRepository = FakeTodoLocalRepository(
            mapperRepository
        )

        val response : MutableLiveData<Response> = MutableLiveData<Response>()
        val mapper = TodoModelMapper()
        val observableUserCase : ObservableUseCaseImpl<Unit,List<TodoDomain>> = ObservableUseCaseImpl(ListTodosUserCase(fakeTodoRepository))

        ViewModelProviders.of(this, ViewModelFactory<ListTodosViewModel>{ ListTodosViewModel(
                observableUserCase,response, mapper)
        }).get(ListTodosViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_todo_activity)

        setRecyclerView()

        vm.response.observe(this, Observer { response -> processResponse(response) })
    }

    private fun setRecyclerView() {
        todoListRecyclerview.layoutManager =  LinearLayoutManager(this);
        todoListRecyclerview.setHasFixedSize(true)
    }

    private fun processResponse(response: Response) {
        Log.d("TEST","RESULT "+response.status)
        when(response.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                renderResponse(response)
                hideLoading()
            }
        }
        //https://proandroiddev.com/mvvm-architecture-using-livedata-rxjava-and-new-dagger-android-injection-639837b1eb6c
    }
    override fun onItemClicked(todoModel: TodoModel) {
        Log.d("Test", "ITEM: "+todoModel.title)
    }

    private fun renderResponse(response: Response) {
        val list : List<TodoModel> = response.data as List<TodoModel>
        var adapter : TodoListAdapter = TodoListAdapter(this, list, this)
        todoListRecyclerview.adapter = adapter
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        todoListRecyclerview.visibility = View.INVISIBLE;
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        todoListRecyclerview.visibility = View.VISIBLE;
    }
}