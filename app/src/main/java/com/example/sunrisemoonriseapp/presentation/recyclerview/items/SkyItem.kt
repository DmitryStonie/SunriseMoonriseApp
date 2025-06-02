package com.example.sunrisemoonriseapp.presentation.recyclerview.items

import androidx.lifecycle.MutableLiveData
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.domain.entities.Moon
import com.example.sunrisemoonriseapp.domain.entities.Place
import com.example.sunrisemoonriseapp.domain.entities.Weather

class SkyItem(
    val resetClickListener: () -> Unit,
    val placeClickListener: () -> Unit,
    val aboutClickListener: () -> Unit,
    val menuSunriseClickListener: () -> Unit,
    val menuNoonClickListener: () -> Unit,
    val menuSunsetClickListener: () -> Unit,
    val menuNightClickListener: () -> Unit,
    val menux1ClickListener: () -> Unit,
    val menux10ClickListener: () -> Unit,
    val menux100ClickListener: () -> Unit,
    val menux1000ClickListener: () -> Unit,
    val timeLiveData: MutableLiveData<Long>,
    val dayLiveData: MutableLiveData<Day>,
    val moonLiveData: MutableLiveData<Moon>,
    val animDurationLiveData: MutableLiveData<Long>,
    val placeLiveData: MutableLiveData<Place>,
    val weatherLiveData: MutableLiveData<Weather>,
) : BaseItem {
    override val type: Int
        get() = BaseItem.Type.SkyItem.value
}