package com.amaro.todolist.presentation.view.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amaro.todolist.R
import com.amaro.todolist.presentation.model.TodoModel
import kotlinx.android.synthetic.main.todo_list_item.view.*

class TodoListAdapter(val context: Context,
                      val itemList: List<TodoModel>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    var actions : TodoListActions? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoModel = itemList[position]
        holder.apply {
            title.text = todoModel.title
            subtitle.text = todoModel.description
            checkBox.isChecked = todoModel.done
            item = todoModel
            itemPosition = position
            addStrikethroughEffect(title, todoModel.done)
        }

        holder.checkBox.setOnCheckedChangeListener {
            _ , value -> actions?.onCheckClickItem(todoModel, position, value)
        }
    }

    private fun addStrikethroughEffect(textView: TextView, should: Boolean) {
        if(should) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    interface TodoListActions {
        fun onClickItem(todoModel: TodoModel?, position: Int)
        fun onCheckClickItem(todoModel: TodoModel?, position: Int, value: Boolean)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title = view.title
        val subtitle = view.subtitle
        val checkBox = view.checkBox
        var item: TodoModel? = null
        var itemPosition = 0

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            actions?.onClickItem(item, itemPosition)
        }
    }
}