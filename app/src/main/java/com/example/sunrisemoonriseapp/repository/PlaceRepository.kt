package com.example.sunrisemoonriseapp.repository

import android.util.Log
import com.example.sunrisemoonriseapp.database.datasources.LocalMoonDataSource
import com.example.sunrisemoonriseapp.database.datasources.LocalPlaceDataSource
import com.example.sunrisemoonriseapp.entities.Moon
import com.example.sunrisemoonriseapp.entities.Place
import com.example.sunrisemoonriseapp.retrofit.moon.MoonRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val localPlaceDateSource: LocalPlaceDataSource
) {
    suspend fun getPlace(): Place? {
        return withContext(Dispatchers.IO) {
            var place: Place? = null
            launch {
                place = localPlaceDateSource.getPlaceById(0)
            }.join()
            return@withContext place
        }
    }

    suspend fun updatePlace(place: Place) {
        return withContext(Dispatchers.IO) {
            localPlaceDateSource.addPlace(
                place
            )
        }
    }


}