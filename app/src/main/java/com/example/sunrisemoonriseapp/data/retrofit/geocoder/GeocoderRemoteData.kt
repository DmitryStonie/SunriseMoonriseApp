package com.example.sunrisemoonriseapp.data.retrofit.geocoder

import com.example.sunrisemoonriseapp.BuildConfig
import javax.inject.Inject

class GeocoderRemoteData @Inject constructor(private val geocoderService: GeocoderService) {
    suspend fun getPlace(latitude: String, longitude: String) =
        geocoderService.getPlaceName(BuildConfig.GEOCODER_API_KEY, "$longitude,$latitude")
}