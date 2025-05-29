package com.example.sunrisemoonriseapp.entities

import android.health.connect.datatypes.units.Temperature
import android.opengl.Visibility

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