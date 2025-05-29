package com.example.sunrisemoonriseapp.database.datasources

import com.example.sunrisemoonriseapp.database.core.daos.MoonDao
import com.example.sunrisemoonriseapp.database.core.entities.MoonEntity
import com.example.sunrisemoonriseapp.entities.Moon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalMoonDataSource @Inject constructor(val moonDao: MoonDao) {
    suspend fun getDayByDate(date: String): Moon? {
        return withContext(Dispatchers.IO) {
            val entity = moonDao.getMoonByDate(date)
            return@withContext if (entity == null) null else
                Moon(
                    entity.date,
                    entity.moon,
                    entity.index,
                    entity.age,
                    entity.phase,
                    entity.distance,
                    entity.illumination,
                    entity.angularDiameter,
                    entity.distanceToSun,
                    entity.sunAngularDiameter
                )
        }
    }
    suspend fun addMoon(moon: Moon) {
        withContext(Dispatchers.IO) {
            moonDao.insertMoon(MoonEntity(
                moon.date,
                moon.moon,
                moon.index,
                moon.age,
                moon.phase,
                moon.distance,
                moon.illumination,
                moon.angularDiameter,
                moon.distanceToSun,
                moon.sunAngularDiameter
            ))
        }
    }
}