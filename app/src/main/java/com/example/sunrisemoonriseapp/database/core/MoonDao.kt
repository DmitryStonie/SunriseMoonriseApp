package com.example.sunrisemoonriseapp.database.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoonDao {

    @Query("SELECT * FROM moons WHERE date = :date")
    fun getMoonByDate(date: String): MoonEntity?

    @Insert
    fun insertMoon(moonEntity: MoonEntity)

}