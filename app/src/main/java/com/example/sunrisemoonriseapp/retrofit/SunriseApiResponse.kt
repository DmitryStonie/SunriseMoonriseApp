package com.example.sunrisemoonriseapp.retrofit

import com.example.sunrisemoonriseapp.repository.Sunrise

data class SunriseApiResponse(
    val results: Sunrise?,
    val status: String
)