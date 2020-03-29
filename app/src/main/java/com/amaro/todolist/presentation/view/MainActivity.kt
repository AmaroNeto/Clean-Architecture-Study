package com.amaro.todolist.presentation.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.fragment.TodoDetailFragment
import com.amaro.todolist.presentation.view.fragment.TodoListFragment
import com.amaro.todolist.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.todo_main_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), TodoListFragment.OnItemClickListener {

    private val TAG = "MainActivity"
    private val mLogger: Logger by inject()
    private var twoPane = false
    private val vm : MainViewModel by viewModel()
    private lateinit var navControler : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_todo_activity)

        navControler =
           NavHostFragment.findNavController(
               supportFragmentManager.findFragmentById(R.id.todo_list_fragment) as NavHostFragment
           )

        setUpActionBar()
        setUpObserver()
        setUpTwoPanelConfig(savedInstanceState)
        setUpDarkMode()
    }

    private fun setUpObserver() {
        vm.response.observe(this, Observer { response -> processResponse(response) })
    }

    private fun setUpTwoPanelConfig(savedInstanceState : Bundle?) {
        if(isTwoPanel()) {
            twoPane = true
            mLogger.v(TAG, "isTwoPanel")

            savedInstanceState?.run {
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

    private fun processResponse(response: Response) {
        mLogger.d(TAG,"response status: ${response.status}")
        when(response.status) {
            Status.SUCCESS -> {
                renderResponse(response)
            }
            Status.ERROR -> {
                // TODO handle error
            }
        }
    }

    private fun renderResponse(response: Response) {
        val todoCount : Int = response.data as Int
        mLogger.v(TAG, "renderResponse $todoCount")
        val toolbarSubtitleTxt = resources.getQuantityString(R.plurals.numberOfActivities, todoCount.toInt(), todoCount)
        toolbarSubtitle.text = toolbarSubtitleTxt
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
            navControler
                .navigate(R.id.action_todoListFragment_to_todoDetailFragment, bundle)
        }
    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.todo_main_toolbar)
        }
        toolbarTitle.text = getString(R.string.app_name)

        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        toolbarInfo.text = currentDate
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_list_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_settings ->  {
                toolbarTitle.text = getString(R.string.settings_title)
                navControler
                    .navigate(R.id.action_todoListFragment_to_settingsFragment)
                return true
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private fun setUpDarkMode() {
        val sharePreference = PreferenceManager.getDefaultSharedPreferences(this)
        val darkModeEnabled = sharePreference.getBoolean(getString(R.string.day_night_key), false)
        if(darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}