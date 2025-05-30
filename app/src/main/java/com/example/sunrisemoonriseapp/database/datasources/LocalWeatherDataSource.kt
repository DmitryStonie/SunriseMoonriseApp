package com.example.sunrisemoonriseapp.database.datasources

import com.example.sunrisemoonriseapp.database.core.daos.WeatherDao
import com.example.sunrisemoonriseapp.entities.Weather
import com.example.sunrisemoonriseapp.mappers.toWeather
import com.example.sunrisemoonriseapp.mappers.toWeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalWeatherDataSource @Inject constructor(val weatherDao: WeatherDao) {
    suspend fun getWeather(date: String): Weather? {
        return withContext(Dispatchers.IO) {
            val entity = weatherDao.getWeatherByDate(date)
            return@withContext entity?.toWeather()
        }
    }

    suspend fun addWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            weatherDao.insertWeather(
                weather.toWeatherEntity()
            )
        }
    }
}