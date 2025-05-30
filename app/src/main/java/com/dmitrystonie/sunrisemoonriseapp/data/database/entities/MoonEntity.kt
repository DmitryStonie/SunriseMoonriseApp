package com.dmitrystonie.sunrisemoonriseapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moons")
class MoonEntity (
    @PrimaryKey
    val date: String,
    val moon: String,
    val index: Int,
    val age: Double,
    val phase: String,
    val distance: Float,
    val illumination: Double,
    val angularDiameter: Double,
    val distanceToSun: Double,
    val sunAngularDiameter: Double
)