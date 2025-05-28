package com.example.sunrisemoonriseapp.retrofit.geocoder

import com.example.sunrisemoonriseapp.retrofit.sun.SunriseApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderService {
    @GET(".")
    suspend fun getPlaceName(@Query("apikey") apiKey: String, @Query("geocode") coordinates: String, @Query("format") date: String = "json") : Response<GeocoderResponse>
}