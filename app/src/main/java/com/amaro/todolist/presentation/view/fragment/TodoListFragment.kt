package com.amaro.todolist.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import com.amaro.todolist.presentation.view.Status
import com.amaro.todolist.presentation.view.adapter.TodoListAdapter
import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : Fragment(), TodoListAdapter.TodoListActions {

    private val viewModel : ListTodosViewModel by viewModel()
    private val mLogger: Logger by inject()

    val TAG = "TodoListFragment"
    lateinit var todoListRecyclerview : RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var newTodoButton: FloatingActionButton
    lateinit var mCallBack : OnItemClickListener

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
        newTodoButton = view.findViewById(R.id.newTodoButton)

        setUpActionBar()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLogger.d(TAG, "onActivityCreated")

        setUpNewTodoButton()
        setUpRecyclerView()
        setUpObserve()
    }

    private fun setUpObserve() {
        viewModel.loadDataResponse.observe(viewLifecycleOwner, Observer { response -> processLoadDataResponse(response) })
        viewModel.updateTodoResponse.observe(viewLifecycleOwner, Observer { response -> processUpdateTodoResponse(response) })
    }

    private fun setUpRecyclerView() {
        mLogger.d(TAG, "setRecyclerView")
        todoListRecyclerview.apply{
            layoutManager =  LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun setUpNewTodoButton() {
        newTodoButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_todoListFragment_to_newTodoFragment)
        }
    }

    private fun processLoadDataResponse(response: Response) {
        mLogger.d(TAG,"load data response status: ${response.status}")
        when(response.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                renderLoadDataResponse(response)
                hideLoading()
            }
            Status.ERROR -> {
                // TODO handle error
            }
        }
    }

    private fun processUpdateTodoResponse(response: Response) {
        mLogger.d(TAG,"update response status: ${response.status}")
        when(response.status) {
            Status.SUCCESS -> {
                renderUpdateResponse(response)
            }
            Status.ERROR -> {
                // TODO handle error
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    private fun renderLoadDataResponse(response: Response) {
        val list : List<TodoModel> = response.data as List<TodoModel>
        mLogger.v(TAG, "renderResponse list "+list.toString())

        val adapter = context?.let { TodoListAdapter(it, list) }
        adapter?.actions = this
        todoListRecyclerview.adapter = adapter
    }

    private fun renderUpdateResponse(response: Response) {
        // TODO
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

    private fun setUpActionBar() {
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        val toolbar = actionBar?.customView
        val toolbarTitle = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle?.text = getString(R.string.app_name)
    }

    override fun onClickItem(todoModel: TodoModel?, position: Int) {
        mLogger.d(TAG,"onItemClicked: ${todoModel?.id} - ${todoModel?.title}")
        todoModel?.let {
            mCallBack.onItemClicked(it)
        }
    }

    override fun onCheckClickItem(todoModel: TodoModel?, position: Int, value: Boolean) {
        mLogger.d(TAG,"onCheckClickItem: ${todoModel?.id} - $position - $value")
        todoModel?.run {
            done = value
            viewModel.updateTodo(todoModel)
        }
    }

}