package com.example.sunrisemoonriseapp.repository

import com.example.sunrisemoonriseapp.database.datasources.LocalSunDataSource
import com.example.sunrisemoonriseapp.entities.Day
import com.example.sunrisemoonriseapp.retrofit.sun.SunRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SunRepository @Inject constructor(
    val remoteData: SunRemoteData,
    val localSunDataSource: LocalSunDataSource
) {
    private suspend fun getDayRemote(
        dateForSun: String,
        latitude: String,
        longitude: String
    ): Day? {
        val response = remoteData.getSunrise(latitude, longitude, dateForSun)
        if (response.body()?.status == "OK") {
            return response.body()!!.results!!
        }
        return null
    }

    suspend fun getDay(
        date: String,
        dateForSun: String,
        latitude: String,
        longitude: String
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
                    day = Day(
                        date,
                        res.sunrise,
                        res.sunset,
                        res.firstLight,
                        res.lastLight,
                        res.dawn,
                        res.dusk,
                        res.solarNoon,
                        res.goldenHour,
                        res.dayLength,
                        res.timezone,
                        res.urcOffset
                    )
                    localSunDataSource.addDay(day!!)
                    return@withContext day
                }
            } else {
                return@withContext day
            }
        }
    }

    suspend fun updateDay(
        date: String,
        dateForSun: String,
        latitude: String,
        longitude: String
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
                day = Day(
                    date,
                    res.sunrise,
                    res.sunset,
                    res.firstLight,
                    res.lastLight,
                    res.dawn,
                    res.dusk,
                    res.solarNoon,
                    res.goldenHour,
                    res.dayLength,
                    res.timezone,
                    res.urcOffset
                )
                localSunDataSource.addDay(day!!)
                return@withContext day
            }
        }
    }

}
