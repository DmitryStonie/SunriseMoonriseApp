package com.example.sunrisemoonriseapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrisemoonriseapp.R
import com.example.sunrisemoonriseapp.recyclerview.items.BaseItem
import com.example.sunrisemoonriseapp.recyclerview.items.DayInfoItem
import com.example.sunrisemoonriseapp.recyclerview.items.SkyItem
import com.example.sunrisemoonriseapp.recyclerview.viewholders.BaseViewHolder
import com.example.sunrisemoonriseapp.recyclerview.viewholders.DayInfoViewHolder
import com.example.sunrisemoonriseapp.recyclerview.viewholders.SkyViewHolder

class RecyclerAdapter(var items: List<BaseItem>) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val viewHolder = when(viewType){
            BaseItem.Type.SkyItem.value -> {
                SkyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_sky_element, parent, false))
            }
            BaseItem.Type.DayInfoItem.value -> {
                DayInfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_ground_day_info_element, parent, false))
            }
            else -> {
                DayInfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_ground_day_info_element, parent, false))
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        when(holder){
            is SkyViewHolder -> {
                holder.bind(items[position] as SkyItem)
            }
            is DayInfoViewHolder -> {
                holder.bind(items[position] as DayInfoItem)
            }
            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<BaseItem>){
        val diffUtilCallback = DiffUtilCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }
}