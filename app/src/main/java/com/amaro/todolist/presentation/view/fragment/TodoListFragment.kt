package com.amaro.todolist.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import com.amaro.todolist.presentation.view.Status
import com.amaro.todolist.presentation.view.adapter.GenericAdapter
import com.amaro.todolist.presentation.viewmodel.ListTodosViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : Fragment(), GenericAdapter.AppAdapterListener<TodoModel> {

    val vm : ListTodosViewModel by viewModel()
    val mLogger: Logger by inject()

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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLogger.d(TAG, "onActivityCreated")

        setNewTodoButton()
        setRecyclerView()
        vm.response.observe(viewLifecycleOwner, Observer { response -> processResponse(response) })
    }

    private fun setRecyclerView() {
        mLogger.d(TAG, "setRecyclerView")
        todoListRecyclerview.layoutManager =  LinearLayoutManager(activity);
        todoListRecyclerview.setHasFixedSize(true)
    }

    private fun setNewTodoButton() {
        newTodoButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_todoListFragment_to_newTodoFragment)
        }
    }

    private fun processResponse(response: Response) {
        mLogger.d(TAG,"response status: ${response.status}")
        when(response.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                renderResponse(response)
                hideLoading()
            }
            Status.ERROR -> {
                // TODO handle error
            }
        }
    }

    override fun onItemClick(model: TodoModel, position: Int) {
        mLogger.v(TAG,"onItemClicked: ${model.id} - ${model.title}")
        mCallBack.onItemClicked(model)
    }

    private fun renderResponse(response: Response) {
        val list : List<TodoModel> = response.data as List<TodoModel>
        mLogger.v(TAG, "renderResponse list "+list.toString())
        val adapter = context?.let { GenericAdapter(list) }
        adapter?.listener = this
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