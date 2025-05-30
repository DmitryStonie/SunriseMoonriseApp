package com.dmitrystonie.sunrisemoonriseapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.MoonEntity

@Dao
interface MoonDao {

    @Query("SELECT * FROM moons WHERE date = :date")
    fun getMoonByDate(date: String): MoonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoon(moonEntity: MoonEntity)

}