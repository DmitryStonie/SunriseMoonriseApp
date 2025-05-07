package com.example.sunrisemoonriseapp.retrofit.sun

import com.example.sunrisemoonriseapp.entities.Day

data class SunriseApiResponse(
    val results: Day?,
    val status: String
)