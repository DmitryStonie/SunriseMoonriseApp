package com.dmitrystonie.sunrisemoonriseapp.data.datasources

import com.dmitrystonie.sunrisemoonriseapp.data.database.daos.WeatherDao
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Weather
import com.dmitrystonie.sunrisemoonriseapp.data.mappers.toWeather
import com.dmitrystonie.sunrisemoonriseapp.data.mappers.toWeatherEntity
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