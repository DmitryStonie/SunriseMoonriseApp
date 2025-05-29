package com.example.sunrisemoonriseapp.recyclerview.items

import androidx.lifecycle.MutableLiveData
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.entities.Weather

class WeatherInfoItem(
    val weatherInfo: MutableLiveData<Weather>) : BaseItem {
    override val type: Int
        get() = BaseItem.Type.WeatherInfoItem.value
}