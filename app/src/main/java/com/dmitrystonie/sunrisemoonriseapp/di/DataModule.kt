package com.dmitrystonie.sunrisemoonriseapp.di

import com.dmitrystonie.sunrisemoonriseapp.data.repositories.MoonRepositoryImpl
import com.dmitrystonie.sunrisemoonriseapp.data.repositories.PlaceRepositoryImpl
import com.dmitrystonie.sunrisemoonriseapp.data.repositories.SunRepositoryImpl
import com.dmitrystonie.sunrisemoonriseapp.data.repositories.WeatherRepositoryImpl
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.MoonRepository
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.PlaceRepository
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.SunRepository
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.WeatherRepository
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
