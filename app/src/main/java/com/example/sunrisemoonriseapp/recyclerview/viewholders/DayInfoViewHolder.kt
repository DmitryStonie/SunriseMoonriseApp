package com.example.sunrisemoonriseapp.recyclerview.viewholders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.example.sunrisemoonriseapp.R
import com.example.sunrisemoonriseapp.getTimeValue
import com.example.sunrisemoonriseapp.getTimeValueShort
import com.example.sunrisemoonriseapp.recyclerview.items.DayInfoItem

class DayInfoViewHolder(val view: View): BaseViewHolder(view){
    val dayLengthText: TextView = view.findViewById<TextView>(R.id.dayLengthText)
    val sunriseText: TextView = view.findViewById<TextView>(R.id.sunriseText)
    val sunsetText: TextView = view.findViewById<TextView>(R.id.sunsetText)
    val cloudinessText: TextView = view.findViewById<TextView>(R.id.cloudinessText)
    val windSpeedText: TextView = view.findViewById<TextView>(R.id.windSpeedText)
    val windDegreeText: TextView = view.findViewById<TextView>(R.id.windDegreeText)
    val compasArrowView: ImageView = view.findViewById<ImageView>(R.id.compasArrow)

    fun bind(item: DayInfoItem){
        // dogshit
        item.dayInfo.observe(view.context as LifecycleOwner) { day ->
            if (day == null) {
                Log.d("DATA", "day is null")
            } else {
                dayLengthText.text = (day.sunset.start - day.sunrise.start).getTimeValueShort()
                sunriseText.text = day.sunrise.start.getTimeValueShort()
                sunsetText.text = day.sunset.start.getTimeValueShort()
            }
        }
        item.weatherInfo.observe(view.context as LifecycleOwner) { weather ->
            cloudinessText.text = "${weather.cloudiness}%"
            windSpeedText.text = "${weather.windSpeed} м/с"
            windDegreeText.text = if(weather.windDegree in 338..360 || weather.windDegree in 0..22){
                "C"
            } else if(weather.windDegree in 23..67){
                "СВ"
            } else if(weather.windDegree in 68..112){
                "В"
            } else if(weather.windDegree in 113..157){
                "ЮВ"
            } else if(weather.windDegree in 158..202){
                "Ю"
            } else if(weather.windDegree in 203..247){
                "ЮЗ"
            } else if(weather.windDegree in 248..292){
                "З"
            } else if(weather.windDegree in 293..337){
                "CЗ"
            } else{
                ""
            }
            compasArrowView.rotation = ((weather.windDegree + 90) % 360).toFloat()
        }
    }

}
