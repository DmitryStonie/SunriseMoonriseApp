package com.dmitrystonie.sunrisemoonriseapp.domain.repositories

import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Moon

interface MoonRepository {
    suspend fun getMoonRemote(date: String): Moon?
    suspend fun getMoon(date: String): Moon?
}