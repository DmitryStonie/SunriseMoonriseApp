package com.example.sunrisemoonriseapp.database.core.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sunrisemoonriseapp.database.core.entities.PlaceEntity

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlaceById(id: Int): PlaceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(placeEntity: PlaceEntity)

}