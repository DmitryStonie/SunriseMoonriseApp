package com.example.sunrisemoonriseapp.repository

import android.util.Log
import com.example.sunrisemoonriseapp.retrofit.moon.MoonDate
import com.example.sunrisemoonriseapp.retrofit.moon.MoonRemoteData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoonRepository @Inject constructor(private val remoteData: MoonRemoteData)
{
    suspend fun getMoon(timestamp: Long): MoonDate? {
        val response = remoteData.getMoon(timestamp)
        Log.d("INFO", response.toString())
        return response.body()?.get(0)
    }
}