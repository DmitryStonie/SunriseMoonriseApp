package com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.viewholders

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.dmitrystonie.sunrisemoonriseapp.R
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.items.WeatherInfoItem
import kotlin.math.round

class WeatherInfoViewHolder(val view: View) : BaseViewHolder(view) {
    val feelsLikeText: TextView = view.findViewById<TextView>(R.id.feelsLikeText)
    val temperatureMaxText: TextView = view.findViewById<TextView>(R.id.temperatureMaxText)
    val temperatureMinText: TextView = view.findViewById<TextView>(R.id.temperatureMinText)
    val humidityText: TextView = view.findViewById<TextView>(R.id.humidityText)
    val pressureText: TextView = view.findViewById<TextView>(R.id.pressureText)

    fun bind(item: WeatherInfoItem) {
        // dogshit
        item.weatherInfo.observe(view.context as LifecycleOwner) { weather ->
            feelsLikeText.text = "${round(weather.temperatureFeelsLike).toInt()}℃"
            temperatureMaxText.text = "${round(weather.temperatureMax).toInt()}℃"
            temperatureMinText.text = "${round(weather.temperatureMin).toInt()}℃"
            humidityText.text = "${weather.humidity}%"
            pressureText.text = "${(weather.pressure / 1.3333).toInt()} мм."
        }
    }

}
