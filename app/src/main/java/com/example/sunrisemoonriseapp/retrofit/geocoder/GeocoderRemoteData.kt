package com.example.sunrisemoonriseapp.retrofit.geocoder

import com.example.sunrisemoonriseapp.BuildConfig
import com.example.sunrisemoonriseapp.retrofit.moon.MoonService
import javax.inject.Inject

class GeocoderRemoteData @Inject constructor(private val geocoderService: GeocoderService) {
    suspend fun getPlace(latitude: String, longitude: String) =
        geocoderService.getPlaceName(BuildConfig.GEOCODER_API_KEY, "$longitude,$latitude")
}