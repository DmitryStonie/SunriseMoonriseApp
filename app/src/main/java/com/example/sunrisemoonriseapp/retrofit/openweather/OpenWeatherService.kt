package com.example.sunrisemoonriseapp.retrofit.openweather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): Response<OpenWeatherApiResponse>
}