package com.example.sunrisemoonriseapp.recyclerview.viewholders

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.sunrisemoonriseapp.R
import com.example.sunrisemoonriseapp.getTimeValue
import com.example.sunrisemoonriseapp.recyclerview.items.DayInfoItem

class DayInfoViewHolder(val view: View): BaseViewHolder(view){
    val dayLengthText: TextView = view.findViewById<TextView>(R.id.dayLengthText)
    val sunriseText: TextView = view.findViewById<TextView>(R.id.sunriseText)
    val noonText: TextView = view.findViewById<TextView>(R.id.noonText)
    val sunsetText: TextView = view.findViewById<TextView>(R.id.sunsetText)

    fun bind(item: DayInfoItem){
        // dogshit
        item.dayInfo.observe(view.context as LifecycleOwner) { day ->
            if (day == null) {
                Log.d("DATA", "day is null")
            } else {
                dayLengthText.text = (day.sunset.start - day.sunrise.start).getTimeValue()
                sunriseText.text = day.sunrise.start.getTimeValue()
                noonText.text = day.solarNoon.start.getTimeValue()
                sunsetText.text = day.sunset.start.getTimeValue()
            }
        }
    }

}
