package com.example.sunrisemoonriseapp.database.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
class DayEntity(
    @PrimaryKey
    val date: String,
    val sunrise: String,
    val sunset: String,
    val firstLight: String,
    val lastLight: String,
    val dawn: String,
    val dusk: String,
    val solarNoon: String,
    val goldenHour: String,
    val dayLength: String,
    val timezone: String,
    val urcOffset: Int
)