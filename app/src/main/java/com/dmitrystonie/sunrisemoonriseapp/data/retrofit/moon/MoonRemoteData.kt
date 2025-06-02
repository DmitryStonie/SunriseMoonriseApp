package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.moon

import javax.inject.Inject

class MoonRemoteData @Inject constructor(private val moonService: MoonService) {
    suspend fun getMoon(timestamp: Long) = moonService.getMoon(timestamp)
}