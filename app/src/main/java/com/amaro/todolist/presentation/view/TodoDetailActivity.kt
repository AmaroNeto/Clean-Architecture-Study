package com.amaro.todolist.presentation.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.amaro.todolist.R

class TodoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_detail_activity)

        if(savedInstanceState == null) {

        }
    }

}