package com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.items.BaseItem

class DiffUtilCallback(private val oldItems: List<BaseItem>, private val newItems: List<BaseItem>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItems[oldItemPosition].type == newItems[newItemPosition].type
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}