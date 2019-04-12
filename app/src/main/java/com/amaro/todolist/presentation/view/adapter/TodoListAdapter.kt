package com.amaro.todolist.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaro.todolist.R
import com.amaro.todolist.presentation.model.TodoModel
import kotlinx.android.synthetic.main.todo_list_item.view.*

class TodoListAdapter(val context : Context,
                      val todoList : List<TodoModel>) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo : TodoModel = todoList.get(position)
        holder.bindView(todo);
    }

    class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(todoModel: TodoModel) {
            val title = itemView.title

            title.text =  todoModel.title
        }

    }
}