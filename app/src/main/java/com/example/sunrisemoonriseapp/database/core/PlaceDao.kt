package com.example.sunrisemoonriseapp.database.core

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places")
    fun getPlaces(): List<PlaceEntity>

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlaceById(id: Int): PlaceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(placeEntity: PlaceEntity)

    @Delete
    fun deletePlace(placeEntity: PlaceEntity)
}