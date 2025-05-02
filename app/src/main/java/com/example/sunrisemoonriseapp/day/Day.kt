package com.example.sunrisemoonriseapp.day

data class Day(
    val nightStart: DayState,
    val dawn: DayState,
    val sunrise: DayState,
    val day: DayState,
    val solarNoon: DayState,
    val goldenHour: DayState,
    val sunset: DayState,
    val dusk: DayState,
    val nightEnd: DayState,
){
    companion object{
        const val NIGHT_START_START_DEFAULT = 0
        const val DAWN_DEFAULT = 21600
        const val SUNRISE_DEFAULT = 23400
        const val DAY_DEFAULT = 23640
        const val SOLAR_NOON_DEFAULT = 43200
        const val GOLDEN_HOUR_DEFAULT = 59360
        const val SUNSET_DEFAULT = 62960
        const val DUSK_DEFAULT = 63200
        const val NIGHT_END_START_DEFAULT = 64600
        const val NIGHT_END_END_DEFAULT = 86400
    }
}