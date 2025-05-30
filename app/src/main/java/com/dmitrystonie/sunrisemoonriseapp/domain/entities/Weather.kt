package com.dmitrystonie.sunrisemoonriseapp.domain.entities

data class Weather(
    val date: String,
    val name: String,
    val description: String,
    val cloudiness: Int,
    val temperature: Float,
    val temperatureFeelsLike: Float,
    val temperatureMin: Float,
    val temperatureMax: Float,
    val windSpeed: Float,
    val windDegree: Int,
    val visibility: Int,
    val humidity: Int,
    val pressure: Int,
    val pressureSeaLevel: Int,
    val pressureGroundLevel: Int,
)