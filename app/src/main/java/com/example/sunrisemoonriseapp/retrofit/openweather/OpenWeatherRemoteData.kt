package com.example.sunrisemoonriseapp.retrofit.openweather

import com.example.sunrisemoonriseapp.BuildConfig
import retrofit2.http.Query
import javax.inject.Inject

class OpenWeatherRemoteData @Inject constructor(private val openWeatherService: OpenWeatherService) {
    suspend fun getWeather(latitude: String, longitude: String) = openWeatherService.getWeather(latitude, longitude,
        BuildConfig.OPEN_WEATHER_API_KEY)
}