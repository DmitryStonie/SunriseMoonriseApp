package com.dmitrystonie.sunrisemoonriseapp.data.repositories

import android.util.Log
import com.dmitrystonie.sunrisemoonriseapp.data.datasources.LocalMoonDataSource
import com.dmitrystonie.sunrisemoonriseapp.data.mappers.toMoon
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.MoonRepository
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Moon
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.moon.MoonRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoonRepositoryImpl @Inject constructor(
    private val remoteData: MoonRemoteData, private val localMoonDataSource: LocalMoonDataSource
) : MoonRepository {
    override suspend fun getMoonRemote(date: String): Moon? {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val dateObj = formatter.parse(date)
        val response = remoteData.getMoon(dateObj!!.time / 1000)
        Log.d("INFO", response.toString())
        val moonDate = response.body()?.get(0)
        return moonDate?.toMoon(date)
    }

    override suspend fun getMoon(date: String): Moon? {
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