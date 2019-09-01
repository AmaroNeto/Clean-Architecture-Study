package com.amaro.todolist.presentation.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.amaro.todolist.R
import com.amaro.todolist.logger.AppLog
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.fragment.TodoDetailFragment

class TodoDetailActivity : AppCompatActivity() {

    private val TAG = "TodoDetailActivity"
    companion object{
        val EXTRA_TODO_MODEL = "todo_model_extra"
    }

    val mLogger = AppLog() //TODO inject dependency in Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_detail_activity)
        mLogger.i(TAG, "onCreate")

        if(savedInstanceState == null) {

            val todoModel = intent.getSerializableExtra(EXTRA_TODO_MODEL) as TodoModel
            todoModel?.let {

                mLogger.i(TAG, "add todoDetailFragment : ${it.title}")
                val fragment  = TodoDetailFragment.newInstance(it)
                val fm = supportFragmentManager
                fm.beginTransaction()
                    .add(R.id.todo_detail_fragment, fragment)
                    .commit();
            }
        }
    }

}