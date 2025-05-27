package com.example.sunrisemoonriseapp.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
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
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_sky_element, parent, false)
                val holder = SkyViewHolder(view)
                val params = holder.view.findViewById<ConstraintLayout>(R.id.scene).layoutParams as RecyclerView.LayoutParams
                params.height = (parent.height / 5 * 4)
                holder.view.layoutParams = params
                holder
            }
            BaseItem.Type.DayInfoItem.value -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_ground_day_info_element, parent, false)
                val holder = DayInfoViewHolder(view)
                val params = holder.view.findViewById<ConstraintLayout>(R.id.element).layoutParams as RecyclerView.LayoutParams
                params.height = (parent.height / 5) + 1
                holder.view.layoutParams = params
                holder
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