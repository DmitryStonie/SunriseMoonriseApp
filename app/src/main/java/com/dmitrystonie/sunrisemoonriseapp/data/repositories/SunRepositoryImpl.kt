package com.dmitrystonie.sunrisemoonriseapp.data.repositories

import com.dmitrystonie.sunrisemoonriseapp.data.datasources.LocalSunDataSource
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.SunRepository
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Day
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.sun.SunRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SunRepositoryImpl @Inject constructor(
    val remoteData: SunRemoteData, val localSunDataSource: LocalSunDataSource
) : SunRepository {
    override suspend fun getDayRemote(
        dateForSun: String, latitude: String, longitude: String
    ): Day? {
        val response = remoteData.getSunrise(latitude, longitude, dateForSun)
        if (response.body()?.status == "OK") {
            return response.body()!!.results!!
        }
        return null
    }

    override suspend fun getDay(
        date: String, dateForSun: String, latitude: String, longitude: String
    ): Day? {
        return withContext(Dispatchers.IO) {
            var day: Day? = null
            launch {
                day = localSunDataSource.getDayByDate(date)
            }.join()
            if (day == null) {
                val res = getDayRemote(dateForSun, latitude, longitude)
                if (res == null) {
                    return@withContext null
                } else {
                    day = res.copy(date = date)
                    localSunDataSource.addDay(day!!)
                    return@withContext day
                }
            } else {
                return@withContext day
            }
        }
    }

    override suspend fun updateDay(
        date: String, dateForSun: String, latitude: String, longitude: String
    ): Day? {
        return withContext(Dispatchers.IO) {
            var day: Day? = null
            val res = getDayRemote(dateForSun, latitude, longitude)
            if (res == null) {
                launch {
                    day = localSunDataSource.getDayByDate(date)
                }.join()
                return@withContext day
            } else {
                day = res.copy(date = date)
                localSunDataSource.addDay(day!!)
                return@withContext day
            }
        }
    }

}
