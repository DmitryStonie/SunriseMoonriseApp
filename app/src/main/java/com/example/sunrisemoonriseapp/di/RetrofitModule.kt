package com.example.sunrisemoonriseapp.di

import com.example.sunrisemoonriseapp.retrofit.MainRemoteData
import com.example.sunrisemoonriseapp.retrofit.SunriseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun providesBaseUrl(): String = "https://api.sunrisesunset.io"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideSunriseService(retrofit: Retrofit): SunriseService = retrofit.create(SunriseService::class.java)

    @Provides
    @Singleton
    fun provideMainRemoteData(sunriseService: SunriseService): MainRemoteData = MainRemoteData(sunriseService)
}