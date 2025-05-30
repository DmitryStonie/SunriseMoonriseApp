package com.example.sunrisemoonriseapp.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class FeatureMember(
    @SerializedName("GeoObject")
    val geoObject: GeoObject
)