package com.amaro.todolist.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.model.TodoModel
import org.koin.android.ext.android.inject

class TodoDetailFragment : Fragment() {

    private val mLogger: Logger by inject()
    private var todoModel : TodoModel? = null
    private val TAG = "TodoDetailFragment"
    private lateinit var mTitle : TextView

    companion object {
        const val ARG_TODO_MODEL = "TodoDetailFragment_TodoModel"

        fun newInstance(todoModel: TodoModel) : TodoDetailFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_TODO_MODEL, todoModel)
            val fragment = TodoDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLogger.i(TAG, "onAttach")

        arguments?.getSerializable(ARG_TODO_MODEL)?.let{
            todoModel = it as TodoModel
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mLogger.i(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.todo_detail_fragment, container, false)
        mTitle = view.findViewById(R.id.title)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLogger.i(TAG, "onActivityCreated")

        savedInstanceState?.let {
            todoModel = it.getSerializable(ARG_TODO_MODEL) as? TodoModel
        }

        mTitle.text = todoModel?.title
    }

    override fun onSaveInstanceState(outState: Bundle) {
        todoModel?.let {
            outState.putSerializable(ARG_TODO_MODEL, it)
            super.onSaveInstanceState(outState)
        }
    }
}