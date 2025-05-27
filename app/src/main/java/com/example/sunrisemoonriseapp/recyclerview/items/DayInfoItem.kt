package com.example.sunrisemoonriseapp.recyclerview.items

import androidx.lifecycle.MutableLiveData
import com.example.sunrisemoonriseapp.day.Day

class DayInfoItem(
    val dayInfo: MutableLiveData<Day>) : BaseItem {
    override val type: Int
        get() = BaseItem.Type.DayInfoItem.value
}