package com.example.sunrisemoonriseapp.repository

import com.example.sunrisemoonriseapp.database.datasources.LocalWeatherDataSource
import com.example.sunrisemoonriseapp.mappers.toWeather
import com.example.sunrisemoonriseapp.retrofit.openweather.OpenWeatherRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.sunrisemoonriseapp.entities.Weather

class WeatherRepository @Inject constructor(
    private val openWeatherRemoteData: OpenWeatherRemoteData,
    private val localDataSource: LocalWeatherDataSource
) {
    suspend fun getWeatherRemote(latitude: String, longitude: String): Weather? {
        return withContext(Dispatchers.IO) {
            val response = openWeatherRemoteData.getWeather(latitude, longitude)
            if (response.isSuccessful) {
                return@withContext response.body()?.toWeather()
            } else {
                return@withContext null
            }
        }
    }

    suspend fun getWeather(date: String): Weather? {
        return withContext(Dispatchers.IO) {
            return@withContext localDataSource.getWeather(date)
        }
    }

    suspend fun saveWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            localDataSource.addWeather(weather)
        }
    }
}