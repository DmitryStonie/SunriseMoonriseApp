package com.example.sunrisemoonriseapp.database.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
class PlaceEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
)