package com.amaro.todolist.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.logger.AppLog
import com.amaro.todolist.presentation.model.TodoModel
import com.amaro.todolist.presentation.view.Response
import com.amaro.todolist.presentation.view.Status
import com.amaro.todolist.presentation.viewmodel.NewTodoViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewTodoFragment : Fragment() {

    val mLogger: Logger by inject()
    val vm : NewTodoViewModel by viewModel()

    private lateinit var saveButton: Button
    private lateinit var titleTli: TextInputLayout
    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionTli: TextInputLayout
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLogger.i(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.todo_new_fragment, container, false)

        saveButton = view.findViewById(R.id.saveButton)
        titleTli = view.findViewById(R.id.titleTli)
        titleEditText = view.findViewById(R.id.titleEditText)
        descriptionTli = view.findViewById(R.id.descritpionTli)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        progressBar = view.findViewById(R.id.progressBar)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSaveButton()
        vm.response.observe(viewLifecycleOwner, Observer { response -> processResponse(response) })
    }

    private fun setSaveButton() {
        saveButton.setOnClickListener {
            Log.v(TAG, "save button clicked")
            if (shouldSave()) {
                vm.create(getTodoModel())
            } else {
                Toast.makeText(activity, R.string.please_correct_errors, Toast.LENGTH_SHORT).show()
            }
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
                Log.e(TAG, "Process response error ${response.error}")
                hideLoading()
                // TODO handle error
            }
        }
    }

    private fun renderResponse(response: Response) {
        val todoModelId = response.data as Long
        Log.v(TAG, "todo id created: $todoModelId")
        if (todoModelId != 0L) {
            view?.findNavController()?.popBackStack()
            Toast.makeText(activity, R.string.todo_created, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, R.string.generic_error_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading() {
        mLogger.v(TAG,"show loading")
        progressBar.visibility = View.VISIBLE
        saveButton.visibility = View.GONE
    }

    private fun hideLoading() {
        mLogger.v(TAG,"hide loading")
        progressBar.visibility = View.GONE
        saveButton.visibility = View.VISIBLE
    }

    private fun getTodoModel(): TodoModel {
        val todoModel = TodoModel(titleEditText.text.toString(), false)
        todoModel.description = descriptionEditText.text.toString()
        return todoModel
    }

    private fun shouldSave(): Boolean {
        clearErrorsMessages()
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        var result = true

        if (title.isEmpty()) {
            titleTli.error = getString(R.string.error_message_empty_string)
            result = false
        }
        if (description.isEmpty()) {
            descriptionTli.error = getString(R.string.error_message_empty_string)
            result = false
        }
        return result
    }

    private fun clearErrorsMessages() {
        titleTli.error = null
        descriptionTli.error = null
    }

    companion object {
        private const val TAG = "NewTodoFragment"
    }
}