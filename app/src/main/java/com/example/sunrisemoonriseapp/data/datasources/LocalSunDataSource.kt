package com.example.sunrisemoonriseapp.data.datasources

import com.example.sunrisemoonriseapp.data.database.daos.DayDao
import com.example.sunrisemoonriseapp.data.database.entities.DayEntity
import com.example.sunrisemoonriseapp.domain.entities.Day
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalSunDataSource @Inject constructor(val dayDao: DayDao) {
    suspend fun getDayByDate(date: String): Day? {
        return withContext(Dispatchers.IO) {
            val entity = dayDao.getDayByDate(date)
            return@withContext if (entity == null) null else
                Day(
                    entity.date,
                    entity.sunrise,
                    entity.sunset,
                    entity.firstLight,
                    entity.lastLight,
                    entity.dawn,
                    entity.dusk,
                    entity.solarNoon,
                    entity.goldenHour,
                    entity.dayLength,
                    entity.timezone,
                    entity.urcOffset
                )
        }
    }
    suspend fun addDay(day: Day) {
        withContext(Dispatchers.IO) {
            dayDao.insertDay(
                DayEntity(
                    day.date ,
                    day.sunrise ?: "",
                    day.sunset ?: "",
                    day.firstLight ?: "",
                    day.lastLight ?: "",
                    day.dawn ?: "",
                    day.dusk ?: "",
                    day.solarNoon ?: "",
                    day.goldenHour ?: "",
                    day.dayLength ?: "",
                    day.timezone ?: "",
                    day.urcOffset ?: 0
                )
            )
        }
    }
}