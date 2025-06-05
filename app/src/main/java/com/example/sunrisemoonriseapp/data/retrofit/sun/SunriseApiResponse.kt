package com.example.sunrisemoonriseapp.data.retrofit.sun

import com.example.sunrisemoonriseapp.domain.entities.Day

data class SunriseApiResponse(
    val results: Day?,
    val status: String
)