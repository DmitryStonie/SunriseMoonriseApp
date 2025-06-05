package com.example.sunrisemoonriseapp.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

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