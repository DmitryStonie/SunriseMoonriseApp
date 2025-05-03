package com.example.sunrisemoonriseapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrisemoonriseapp.R

class EventAdapter(var events: List<Item>) : RecyclerView.Adapter<EventViewHolder>() {
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_element, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int
    ) {
        holder.titleView.text = events[position].title
        holder.valueView.text = events[position].text
        holder.view.setOnClickListener { onClickListener.onClick(position) }
    }

    override fun getItemCount(): Int {
        return events.size
    }
    fun interface OnClickListener {
        fun onClick(itemPosition: Int)
    }
}