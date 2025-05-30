package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.moon

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoonService {
    @GET("moonphases/")
    suspend fun getMoon(@Query("d") timestamp: Long) : Response<List<MoonDate>>
}