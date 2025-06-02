package com.dmitrystonie.sunrisemoonriseapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitrystonie.sunrisemoonriseapp.data.database.daos.DayDao
import com.dmitrystonie.sunrisemoonriseapp.data.database.daos.MoonDao
import com.dmitrystonie.sunrisemoonriseapp.data.database.daos.PlaceDao
import com.dmitrystonie.sunrisemoonriseapp.data.database.daos.WeatherDao
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.DayEntity
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.MoonEntity
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.PlaceEntity
import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.WeatherEntity

@Database(entities = [DayEntity::class, MoonEntity::class, PlaceEntity::class, WeatherEntity::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun moonDao(): MoonDao
    abstract fun placeDao(): PlaceDao
    abstract fun weatherDao(): WeatherDao
}