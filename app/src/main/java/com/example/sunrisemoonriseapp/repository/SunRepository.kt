package com.example.sunrisemoonriseapp.repository

import com.example.sunrisemoonriseapp.retrofit.MainRemoteData
import com.example.sunrisemoonriseapp.retrofit.SunriseApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SunRepository @Inject constructor(private val remoteData: MainRemoteData)
{
    suspend fun getSunrise(latitude: String, longitude: String): Sunrise? {
        val response = remoteData.getSunrise(latitude, longitude)
        if(response.body()?.status == "OK"){
            return response.body()!!.results!!
        }
        return null
    }
}