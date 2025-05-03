package com.example.sunrisemoonriseapp.recyclerview

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(private val oldItems: List<Item>, private val newItems: List<Item>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItems[oldItemPosition].title == newItems[newItemPosition].title
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItems[oldItemPosition].text == newItems[newItemPosition].text
    }
}