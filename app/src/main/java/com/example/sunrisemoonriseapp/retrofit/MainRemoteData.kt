package com.example.sunrisemoonriseapp.retrofit

import javax.inject.Inject

class MainRemoteData @Inject constructor(private val sunriseService: SunriseService) {
    suspend fun getSunrise(latitude: String, longitude: String) = sunriseService.getSunrise(latitude, longitude)
}