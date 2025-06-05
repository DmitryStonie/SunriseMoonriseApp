package com.example.sunrisemoonriseapp.di

import com.example.sunrisemoonriseapp.data.repositories.MoonRepositoryImpl
import com.example.sunrisemoonriseapp.data.repositories.PlaceRepositoryImpl
import com.example.sunrisemoonriseapp.data.repositories.SunRepositoryImpl
import com.example.sunrisemoonriseapp.data.repositories.WeatherRepositoryImpl
import com.example.sunrisemoonriseapp.domain.repositories.MoonRepository
import com.example.sunrisemoonriseapp.domain.repositories.PlaceRepository
import com.example.sunrisemoonriseapp.domain.repositories.SunRepository
import com.example.sunrisemoonriseapp.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DateModule {
    @Binds
    abstract fun provideMoonRepository(moonRepositoryImpl: MoonRepositoryImpl): MoonRepository

    @Binds
    abstract fun providePlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository

    @Binds
    abstract fun provideSunRepository(sunRepositoryImpl: SunRepositoryImpl): SunRepository

    @Binds
    abstract fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

}
