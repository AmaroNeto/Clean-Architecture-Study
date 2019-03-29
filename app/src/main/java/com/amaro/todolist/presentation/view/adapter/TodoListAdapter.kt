package com.amaro.todolist.presentation.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaro.todolist.presentation.model.TodoModel

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(todoModel: TodoModel) {
            //val title = itemView.
        }

    }
}