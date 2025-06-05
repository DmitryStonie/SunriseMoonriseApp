package com.example.sunrisemoonriseapp.data.retrofit.openweather

import android.util.Log
import com.example.sunrisemoonriseapp.BuildConfig
import okhttp3.Response
import javax.inject.Inject

class OpenWeatherRemoteData @Inject constructor(private val openWeatherService: OpenWeatherService) {
    suspend fun getWeather(latitude: String, longitude: String): retrofit2.Response<OpenWeatherApiResponse>? {
        try{
            return openWeatherService.getWeather(
                latitude, longitude,
                BuildConfig.OPEN_WEATHER_API_KEY
            )
        } catch (e: Exception){
            Log.d("ERROR", e.message.toString())
            return null
        }
    }
}