package com.gevorg.mvp.base_mvp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any>(private val layoutId: Int, val list: MutableList<T>) :
    RecyclerView.Adapter<BaseViewHolder>() {

    open fun onBind(
        holder: BaseViewHolder,
        it: T
    ) {
    }

    open fun onBind(
        holder: BaseViewHolder,
        list: MutableList<T>,
        position: Int
    ) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBind(holder, list[position])
        onBind(holder, list, position)
    }

    override fun getItemCount() = list.size

    fun updateData(newList: MutableList<T>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItem(listItem: T) {
        list.add(listItem)
        notifyItemInserted(list.size)
    }
}