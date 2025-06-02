package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.sun

import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Day

data class SunriseApiResponse(
    val results: Day?,
    val status: String
)