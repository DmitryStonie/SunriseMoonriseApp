package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.openweather

import com.google.gson.annotations.SerializedName

data class OpenWeatherApiResponse (
    @SerializedName("weather")
    val weather: List<WeatherData>,
    @SerializedName("main")
    val main: MainData,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: WindData,
    @SerializedName("clouds")
    val clouds: CloudsData,
)