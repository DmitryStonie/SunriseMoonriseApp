package com.example.sunrisemoonriseapp.domain.repositories

import com.example.sunrisemoonriseapp.domain.entities.Moon

interface MoonRepository {
    suspend fun getMoonRemote(date: String): Moon?
    suspend fun getMoon(date: String): Moon?
}