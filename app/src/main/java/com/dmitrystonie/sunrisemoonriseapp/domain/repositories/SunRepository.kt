package com.dmitrystonie.sunrisemoonriseapp.domain.repositories

import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Day

interface SunRepository {

    suspend fun getDayRemote(
        dateForSun: String,
        latitude: String,
        longitude: String
    ): Day?

    suspend fun getDay(
        date: String, dateForSun: String, latitude: String, longitude: String
    ): Day?

    suspend fun updateDay(
        date: String, dateForSun: String, latitude: String, longitude: String
    ): Day?

}
