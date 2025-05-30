package com.dmitrystonie.sunrisemoonriseapp.data.datasources

import com.dmitrystonie.sunrisemoonriseapp.data.database.daos.PlaceDao
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.PlaceEntity
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalPlaceDataSource @Inject constructor(val placeDao: PlaceDao) {
    suspend fun getPlaceById(id: Int): Place? {
        return withContext(Dispatchers.IO) {
            val entity = placeDao.getPlaceById(id)
            return@withContext if (entity == null) null else
                Place(
                    entity.latitude,
                    entity.longitude,
                    entity.name
                )
        }
    }
    suspend fun addPlace(place: Place) {
        withContext(Dispatchers.IO) {
            placeDao.insertPlace(
                PlaceEntity(
                    0,
                    place.name,
                    place.latitude,
                    place.longitude
                )
            )
        }
    }
}