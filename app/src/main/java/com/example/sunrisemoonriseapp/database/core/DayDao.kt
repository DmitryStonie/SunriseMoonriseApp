package com.example.sunrisemoonriseapp.database.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayDao {

    @Query("SELECT * FROM days WHERE date = :date")
    fun getDayByDate(date: String): DayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(dayEntity: DayEntity)

}