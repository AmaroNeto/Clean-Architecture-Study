package com.amaro.todolist.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T: AdapterModel>(val list : List<T>):
    RecyclerView.Adapter<GenericAdapter<T>.ViewHolder<ViewDataBinding>>(), Filterable {

    private var searchList : List<T> = list
    var listener : AppAdapterListener<T>? = null

    interface AppAdapterListener<T> {
        fun onItemClick(model: T, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder<ViewDataBinding> {
        val bind : ViewDataBinding =
            DataBindingUtil.bind(LayoutInflater.from(parent.context).inflate(viewType, parent, false))!!
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position : Int) {
        val model : T = searchList[position]
        holder.getBinding().setVariable(BR.model, model)
        holder.getBinding().executePendingBindings()

        holder.getBinding().root.setOnClickListener{
            listener?.onItemClick(model,position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return searchList.get(position).layoutId()
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun getFilter(): Filter? {
        return null
    }

    inner class ViewHolder<V : ViewDataBinding>(private val view : V) : RecyclerView.ViewHolder(view.root){
        fun getBinding() : V {
            return view
        }
    }
}