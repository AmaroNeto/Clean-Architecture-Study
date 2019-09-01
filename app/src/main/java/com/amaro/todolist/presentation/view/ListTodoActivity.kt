package com.amaro.todolist.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.logger.AppLog
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.fragment.TodoDetailFragment
import com.amaro.todolist.presentation.view.fragment.TodoListFragment
import org.koin.android.ext.android.inject


class ListTodoActivity : AppCompatActivity(), TodoListFragment.OnItemClickListener {

    val TAG = "ListTodoActivity"
    val mLogger: Logger by inject()
    var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_todo_activity)

        if(isTwoPanel()) {
            twoPane = true
            mLogger.v(TAG, "isTwoPanel")

            if(savedInstanceState == null) {
                val fm = supportFragmentManager
                val todoDetailFragment = TodoDetailFragment()

                fm.beginTransaction()
                    .add(R.id.todo_detail_fragment,todoDetailFragment)
                    .commit()
                mLogger.v(TAG, "added TodoDetailFragment")
            }
        } else {
            twoPane = false
        }

    }

    fun isTwoPanel() : Boolean {
        return findViewById<FrameLayout>(R.id.todo_detail_fragment) != null
    }

    override fun onItemClicked(todoModel: TodoModel) {

        mLogger.v(TAG, "item clicked: ${todoModel.title}")
        if(twoPane) {
            val todoDetailFragment = TodoDetailFragment.newInstance(todoModel)
            supportFragmentManager.beginTransaction()
                .replace(R.id.todo_detail_fragment, todoDetailFragment)
                .commit()
        } else {
            val intent = Intent(this, TodoDetailActivity::class.java)
            intent.putExtra(TodoDetailActivity.EXTRA_TODO_MODEL, todoModel)
            startActivity(intent)
        }
    }

}