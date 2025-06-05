package com.example.sunrisemoonriseapp.di

import com.example.sunrisemoonriseapp.data.retrofit.openweather.OpenWeatherRemoteData
import com.example.sunrisemoonriseapp.data.retrofit.openweather.OpenWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitOpenWeatherModule {

    @Provides
    @Named("openWeatherApiUrl")
    fun providesBaseUrl(): String = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    @Named("openWeather")
    fun provideOpenWeatherRetrofit(@Named("openWeatherApiUrl") baseUrl : String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideOpenWeatherService(@Named("openWeather") retrofit: Retrofit): OpenWeatherService = retrofit.create(
        OpenWeatherService::class.java)

    @Provides
    @Singleton
    fun provideOpenWeatherRemoteData(openWeatherService: OpenWeatherService): OpenWeatherRemoteData =
        OpenWeatherRemoteData(openWeatherService)
}