package com.example.sunrisemoonriseapp.di

import com.example.sunrisemoonriseapp.retrofit.geocoder.GeocoderRemoteData
import com.example.sunrisemoonriseapp.retrofit.geocoder.GeocoderService
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
object RetrofitGeocoderModule {

    @Provides
    @Named("geocoderApiUrl")
    fun providesBaseUrl(): String = "https://geocode-maps.yandex.ru/v1/"

    @Provides
    @Singleton
    @Named("geocoder")
    fun provideGeocoderRetrofit(@Named("geocoderApiUrl") BASE_URL : String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideGeocoderService(@Named("geocoder") retrofit: Retrofit): GeocoderService = retrofit.create(
        GeocoderService::class.java)

    @Provides
    @Singleton
    fun provideGeocoderRemoteData(geocoderService: GeocoderService): GeocoderRemoteData =
        GeocoderRemoteData(geocoderService)
}