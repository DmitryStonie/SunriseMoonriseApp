package com.dmitrystonie.sunrisemoonriseapp.domain.repositories

import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Weather

interface WeatherRepository {
    suspend fun getWeatherRemote(latitude: String, longitude: String): Weather?

    suspend fun getWeather(date: String): Weather?

    suspend fun saveWeather(weather: Weather)
}