package com.example.sunrisemoonriseapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sunrisemoonriseapp.data.database.entities.DayEntity

@Dao
interface DayDao {

    @Query("SELECT * FROM days WHERE date = :date")
    fun getDayByDate(date: String): DayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(dayEntity: DayEntity)

}