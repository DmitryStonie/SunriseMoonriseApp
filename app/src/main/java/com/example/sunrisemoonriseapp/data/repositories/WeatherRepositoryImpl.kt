package com.example.sunrisemoonriseapp.data.repositories

import com.example.sunrisemoonriseapp.data.datasources.LocalWeatherDataSource
import com.example.sunrisemoonriseapp.domain.repositories.WeatherRepository
import com.example.sunrisemoonriseapp.data.mappers.toWeather
import com.example.sunrisemoonriseapp.data.retrofit.openweather.OpenWeatherRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.sunrisemoonriseapp.domain.entities.Weather
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherRemoteData: OpenWeatherRemoteData,
    private val localDataSource: LocalWeatherDataSource
) : WeatherRepository {
    override suspend fun getWeatherRemote(
        latitude: String,
        longitude: String,
        date: String
    ): Weather? {
        return withContext(Dispatchers.IO) {
            val response = openWeatherRemoteData.getWeather(latitude, longitude)
            if (response == null) {
                return@withContext getWeather(date)
            } else if (response.isSuccessful) {
                return@withContext response.body()?.toWeather()
            } else {
                return@withContext getWeather(date)
            }
        }
    }

    override suspend fun getWeather(date: String): Weather? {
        return withContext(Dispatchers.IO) {
            localDataSource.getWeather(date)
        }
    }

    override suspend fun saveWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            localDataSource.addWeather(weather)
        }
    }
}