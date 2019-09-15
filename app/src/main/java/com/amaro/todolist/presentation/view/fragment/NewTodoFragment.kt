package com.amaro.todolist.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amaro.todolist.R
import com.amaro.todolist.logger.AppLog
import org.koin.android.ext.android.inject

class NewTodoFragment : Fragment() {

    val mLogger: AppLog by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLogger.i(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.todo_new_fragment, container, false)
        return view
    }

    companion object {
        private const val TAG = "NewTodoFragment"
    }
}