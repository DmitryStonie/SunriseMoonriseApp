package com.dmitrystonie.sunrisemoonriseapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
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