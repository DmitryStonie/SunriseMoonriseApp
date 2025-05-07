package com.example.sunrisemoonriseapp.database.core

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DayEntity::class, MoonEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun moonDao(): MoonDao
}