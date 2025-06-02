package com.example.sunrisemoonriseapp.presentation.recyclerview.items

import androidx.lifecycle.MutableLiveData
import com.example.sunrisemoonriseapp.domain.entities.Weather

class WeatherInfoItem(
    val weatherInfo: MutableLiveData<Weather>) : BaseItem {
    override val type: Int
        get() = BaseItem.Type.WeatherInfoItem.value
}