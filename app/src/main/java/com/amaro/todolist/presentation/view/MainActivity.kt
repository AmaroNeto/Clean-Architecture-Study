package com.amaro.todolist.presentation.view

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.fragment.TodoDetailFragment
import com.amaro.todolist.presentation.view.fragment.TodoListFragment
import kotlinx.android.synthetic.main.todo_main_toolbar.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), TodoListFragment.OnItemClickListener {

    val TAG = "MainActivity"
    val mLogger: Logger by inject()
    var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_todo_activity)

        setUpActionBar()

        if(isTwoPanel()) {
            twoPane = true
            mLogger.v(TAG, "isTwoPanel")

            if(savedInstanceState == null) {
                val host = NavHostFragment.create(R.navigation.tablet_nav_graph)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.todo_detail_container, host)
                    .setPrimaryNavigationFragment(host)
                    .commit()
                mLogger.v(TAG, "added TodoDetailFragment")
            }
        } else {
            twoPane = false
        }

    }

    fun isTwoPanel(): Boolean {
        return resources.getBoolean(R.bool.isTablet)
    }

    override fun onItemClicked(todoModel: TodoModel) {

        mLogger.v(TAG, "item clicked: ${todoModel.title}")
        val bundle = bundleOf(TodoDetailFragment.ARG_TODO_MODEL to todoModel)

        if(twoPane) {
            val host = NavHostFragment.create(R.navigation.tablet_nav_graph, bundle)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.todo_detail_container, host)
                .setPrimaryNavigationFragment(host)
                .commit()
        } else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.todo_list_fragment) as NavHostFragment
            NavHostFragment.findNavController(navHostFragment)
                .navigate(R.id.action_todoListFragment_to_todoDetailFragment, bundle)
        }
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.todo_main_toolbar)
        toolbarTitle.text = getString(R.string.app_name)

        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        toolbarInfo.text = currentDate
    }

}