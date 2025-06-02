package com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.items

import androidx.lifecycle.MutableLiveData
import com.dmitrystonie.sunrisemoonriseapp.day.Day
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Weather

class DayInfoItem(
    val dayInfo: MutableLiveData<Day>,
    val weatherInfo: MutableLiveData<Weather>) : BaseItem {
    override val type: Int
        get() = BaseItem.Type.DayInfoItem.value
}