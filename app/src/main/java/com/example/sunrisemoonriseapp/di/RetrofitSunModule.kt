package com.example.sunrisemoonriseapp.di

import com.example.sunrisemoonriseapp.data.retrofit.sun.SunRemoteData
import com.example.sunrisemoonriseapp.data.retrofit.sun.SunriseService
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
object RetrofitSunModule {

    @Provides
    @Named("sunApiUrl")
    fun providesBaseUrl(): String = "https://api.sunrisesunset.io"

    @Provides
    @Singleton
    @Named("sun")
    fun provideSunRetrofit(@Named("sunApiUrl") baseUrl : String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideSunriseService(@Named("sun") retrofit: Retrofit): SunriseService = retrofit.create(SunriseService::class.java)

    @Provides
    @Singleton
    fun provideMainRemoteData(sunriseService: SunriseService): SunRemoteData = SunRemoteData(sunriseService)
}