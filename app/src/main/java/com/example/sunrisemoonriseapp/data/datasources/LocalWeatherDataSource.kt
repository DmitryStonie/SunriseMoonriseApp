package com.example.sunrisemoonriseapp.data.datasources

import com.example.sunrisemoonriseapp.data.database.daos.WeatherDao
import com.example.sunrisemoonriseapp.domain.entities.Weather
import com.example.sunrisemoonriseapp.data.mappers.toWeather
import com.example.sunrisemoonriseapp.data.mappers.toWeatherEntity
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