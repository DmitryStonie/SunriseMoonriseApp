package com.example.sunrisemoonriseapp.retrofit.sun

import javax.inject.Inject

class SunRemoteData @Inject constructor(private val sunriseService: SunriseService) {
    suspend fun getSunrise(latitude: String, longitude: String, date: String) = sunriseService.getSunrise(latitude, longitude, date)
}