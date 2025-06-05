package com.example.sunrisemoonriseapp.domain.repositories

import com.example.sunrisemoonriseapp.domain.entities.Place


interface PlaceRepository {
    suspend fun getLocalPlace(): Place?
    suspend fun getPlaceName(place: Place): String?
    suspend fun updatePlace(place: Place)
}