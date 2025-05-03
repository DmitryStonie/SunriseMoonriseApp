package com.example.sunrisemoonriseapp.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sunrisemoonriseapp.R

class EventViewHolder(val view: View) : ViewHolder(view) {
    var titleView: TextView
    var valueView: TextView

    init{
        titleView = view.findViewById<TextView>(R.id.itemName)
        valueView = view.findViewById<TextView>(R.id.itemValue)
    }
}