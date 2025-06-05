package com.example.sunrisemoonriseapp.di

import com.example.sunrisemoonriseapp.data.retrofit.moon.MoonRemoteData
import com.example.sunrisemoonriseapp.data.retrofit.moon.MoonService
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
object RetrofitMoonModule {

    @Provides
    @Named("moonApiUrl")
    fun providesBaseUrl(): String = "https://api.farmsense.net/v1/"

    @Provides
    @Singleton
    @Named("moon")
    fun provideMoonRetrofit(@Named("moonApiUrl") baseUrl : String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideMoonService(@Named("moon") retrofit: Retrofit): MoonService = retrofit.create(MoonService::class.java)

    @Provides
    @Singleton
    fun provideMoonRemoteData(moonService: MoonService): MoonRemoteData = MoonRemoteData(moonService)
}