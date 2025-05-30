package com.example.sunrisemoonriseapp.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sunrisemoonriseapp.database.core.daos.DayDao
import com.example.sunrisemoonriseapp.database.core.daos.MoonDao
import com.example.sunrisemoonriseapp.database.core.daos.PlaceDao
import com.example.sunrisemoonriseapp.database.core.daos.WeatherDao
import com.example.sunrisemoonriseapp.database.core.entities.DayEntity
import com.example.sunrisemoonriseapp.database.core.entities.MoonEntity
import com.example.sunrisemoonriseapp.database.core.entities.PlaceEntity
import com.example.sunrisemoonriseapp.database.core.entities.WeatherEntity

@Database(entities = [DayEntity::class, MoonEntity::class, PlaceEntity::class, WeatherEntity::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun moonDao(): MoonDao
    abstract fun placeDao(): PlaceDao
    abstract fun weatherDao(): WeatherDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `weather` (`date` TEXT NOT NULL, `name` TEXT NOT NULL, " +
                "`description` TEXT NOT NULL, `cloudiness` INTEGER NOT NULL, `temperature` REAL NOT NULL, " +
                "`temperatureFeelsLike` REAL NOT NULL, `temperatureMin` REAL NOT NULL, `temperatureMax` REAL NOT NULL, " +
                "`windSpeed` REAL NOT NULL, `windDegree` INTEGER NOT NULL, `visibility` INTEGER NOT NULL, " +
                "`humidity` INTEGER NOT NULL, `pressure` INTEGER NOT NULL, `pressureSeaLevel` INTEGER NOT NULL, " +
                "`pressureGroundLevel` INTEGER NOT NULL, PRIMARY KEY(`date`))")
    }
}