package com.example.sunrisemoonriseapp.repository

import android.util.Log
import com.example.sunrisemoonriseapp.database.datasources.LocalMoonDataSource
import com.example.sunrisemoonriseapp.entities.Moon
import com.example.sunrisemoonriseapp.retrofit.moon.MoonRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoonRepository @Inject constructor(
    private val remoteData: MoonRemoteData,
    private val localMoonDataSource: LocalMoonDataSource
) {
    private suspend fun getMoonRemote(date: String): Moon? {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val dateObj = formatter.parse(date)
        val response = remoteData.getMoon(dateObj!!.time / 1000)
        Log.d("INFO", response.toString())
        val moonDate = response.body()?.get(0)
        return if (moonDate == null) null else
            Moon(
                date,
                moonDate.moon.getOrNull(0) ?: "",
                moonDate.index,
                moonDate.age,
                moonDate.phase,
                moonDate.distance,
                moonDate.illumination,
                moonDate.angularDiameter,
                moonDate.distanceToSun,
                moonDate.sunAngularDiameter
            )
    }

    suspend fun getMoon(date: String): Moon? {
        return withContext(Dispatchers.IO) {
            var moon: Moon? = null
            launch {
                moon = localMoonDataSource.getDayByDate(date)
            }.join()
            if (moon == null) {
                moon = getMoonRemote(date)
                if (moon == null) {
                    return@withContext null
                } else {
                    localMoonDataSource.addMoon(moon!!)
                    return@withContext moon
                }
            } else {
                return@withContext moon
            }
        }
    }


}