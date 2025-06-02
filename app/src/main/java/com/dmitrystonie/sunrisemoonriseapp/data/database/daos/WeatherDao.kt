package com.dmitrystonie.sunrisemoonriseapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE date = :date")
    fun getWeatherByDate(date: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity: WeatherEntity)

}