package com.dmitrystonie.sunrisemoonriseapp.domain.repositories

import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Place


interface PlaceRepository {
    suspend fun getLocalPlace(): Place?
    suspend fun getPlaceName(place: Place): String?
    suspend fun updatePlace(place: Place)
}