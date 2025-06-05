package com.example.sunrisemoonriseapp.presentation.recyclerview.items

import androidx.lifecycle.MutableLiveData
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.domain.entities.Weather

class DayInfoItem(
    val dayInfo: MutableLiveData<Day>,
    val weatherInfo: MutableLiveData<Weather>) : BaseItem {
    override val type: Int
        get() = BaseItem.Type.DayInfoItem.value
}