package com.example.sunrisemoonriseapp.recyclerview.items


sealed interface BaseItem {
    enum class Type(val value: Int) {
        SkyItem(0),
        DayInfoItem(1),
        WeatherInfoItem(2),
    }
    val type: Int
}