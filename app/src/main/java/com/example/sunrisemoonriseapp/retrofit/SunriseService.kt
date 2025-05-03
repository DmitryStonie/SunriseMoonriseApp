package com.example.sunrisemoonriseapp.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SunriseService {
    @GET("/json")
    suspend fun getSunrise(@Query("lat") latitude: String, @Query("lng") longitude: String, @Query("time_format") timeFormat: Int = 24) : Response<SunriseApiResponse>
}