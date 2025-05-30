package com.dmitrystonie.sunrisemoonriseapp.data.repositories

import com.dmitrystonie.sunrisemoonriseapp.data.datasources.LocalWeatherDataSource
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.WeatherRepository
import com.dmitrystonie.sunrisemoonriseapp.data.mappers.toWeather
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.openweather.OpenWeatherRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Weather
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherRemoteData: OpenWeatherRemoteData,
    private val localDataSource: LocalWeatherDataSource
) : WeatherRepository {
    override suspend fun getWeatherRemote(latitude: String, longitude: String): Weather? {
        return withContext(Dispatchers.IO) {
            val response = openWeatherRemoteData.getWeather(latitude, longitude)
            if (response.isSuccessful) {
                return@withContext response.body()?.toWeather()
            } else {
                return@withContext null
            }
        }
    }

    override suspend fun getWeather(date: String): Weather? {
        return withContext(Dispatchers.IO) {
            return@withContext localDataSource.getWeather(date)
        }
    }

    override suspend fun saveWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            localDataSource.addWeather(weather)
        }
    }
}