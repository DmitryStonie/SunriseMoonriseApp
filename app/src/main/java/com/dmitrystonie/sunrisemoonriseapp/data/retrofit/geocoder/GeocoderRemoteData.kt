package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder

import com.dmitrystonie.sunrisemoonriseapp.BuildConfig
import javax.inject.Inject

class GeocoderRemoteData @Inject constructor(private val geocoderService: GeocoderService) {
    suspend fun getPlace(latitude: String, longitude: String) =
        geocoderService.getPlaceName(BuildConfig.GEOCODER_API_KEY, "$longitude,$latitude")
}