package com.amaro.todolist.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.amaro.todolist.R
import com.amaro.todolist.data.local.AppDataBase
import com.amaro.todolist.data.local.dao.TodoDao
import com.amaro.todolist.data.local.repository.FakeTodoLocalRepository
import com.amaro.todolist.data.local.repository.TodoLocalRepository
import com.amaro.todolist.data.mapper.TodoLocalMapper
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.executor.ObservableUseCaseImpl
import com.amaro.todolist.domain.usercases.ListTodosUserCase
import com.amaro.todolist.logger.AppLog
import com.amaro.todolist.presentation.mapper.TodoModelMapper
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import com.amaro.todolist.presentation.view.Status
import com.amaro.todolist.presentation.view.adapter.TodoListAdapter
import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import com.amaro.todolist.presentation.viewmodel.ViewModelFactory

class TodoListFragment : Fragment(), TodoListAdapter.TodoListAdapterCallback {

    val vm : ListTodosViewModel by lazy {

        val log = AppLog();

        val db : AppDataBase = Room.databaseBuilder(
            activity!!.application,
            AppDataBase::class.java, "todo-db"
        ).build()
        val todoDao : TodoDao = db.todoDao()

        val mapperRepository: TodoLocalMapper = TodoLocalMapper()
        val todoRepository : TodoLocalRepository = TodoLocalRepository(
            todoDao,
            mapperRepository,
            log
        )

        //Fake data ------------
        val fakeTodoRepository : FakeTodoLocalRepository = FakeTodoLocalRepository(
            mapperRepository
        )

        val response : MutableLiveData<Response> = MutableLiveData<Response>()
        val mapper = TodoModelMapper()
        val observableUserCase : ObservableUseCaseImpl<Unit, List<TodoDomain>> = ObservableUseCaseImpl(ListTodosUserCase(fakeTodoRepository,log))

        ViewModelProviders.of(this, ViewModelFactory<ListTodosViewModel>{ ListTodosViewModel(
            observableUserCase,response, mapper)
        }).get(ListTodosViewModel::class.java)
    }

    val mLogger = AppLog() //TODO inject dependency in Logger
    val TAG = "TodoListFragment"
    lateinit var todoListRecyclerview : RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var mCallBack : OnItemClickListener;

    interface OnItemClickListener {
        fun onItemClicked(todoModel: TodoModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLogger.d(TAG, "TodoListFragment attached")

        try {
            mCallBack = context as OnItemClickListener
            mLogger.v(TAG, "added listener")
        } catch (e : ClassCastException) {
            mLogger.e(TAG,context.toString()
                    + " must implement OnItemClickListener")
            throw ClassCastException(context.toString()
                    + " must implement OnItemClickListener");
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mLogger.d(TAG, "onCreateView")

        val view = inflater.inflate(R.layout.todo_list_fragment, container, false)
        todoListRecyclerview = view.findViewById(R.id.todoListRecyclerview)
        progressBar = view.findViewById(R.id.progressBar)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLogger.d(TAG, "onActivityCreated")

        setRecyclerView()
        vm.response.observe(viewLifecycleOwner, Observer { response -> processResponse(response) })
    }

    private fun setRecyclerView() {
        mLogger.d(TAG, "setRecyclerView")
        todoListRecyclerview.layoutManager =  LinearLayoutManager(activity);
        todoListRecyclerview.setHasFixedSize(true)
    }

    private fun processResponse(response: Response) {
        mLogger.d(TAG,"status result: ${response.status}")
        when(response.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                renderResponse(response)
                hideLoading()
            }
        }
    }
    override fun onItemClicked(todoModel: TodoModel) {
        mLogger.v(TAG,"onItemClicked: ${todoModel.id} - ${todoModel.title}")
        mCallBack.onItemClicked(todoModel)
    }

    private fun renderResponse(response: Response) {
        mLogger.v(TAG, "renderResponse "+response.status)
        val list : List<TodoModel> = response.data as List<TodoModel>
        val adapter = context?.let { TodoListAdapter(it, list, this) }
        todoListRecyclerview.adapter = adapter
    }

    private fun showLoading() {
        mLogger.v(TAG,"show loading")
        progressBar.visibility = View.VISIBLE
        todoListRecyclerview.visibility = View.INVISIBLE;
    }

    private fun hideLoading() {
        mLogger.v(TAG,"hide loading")
        progressBar.visibility = View.GONE
        todoListRecyclerview.visibility = View.VISIBLE;
    }
}