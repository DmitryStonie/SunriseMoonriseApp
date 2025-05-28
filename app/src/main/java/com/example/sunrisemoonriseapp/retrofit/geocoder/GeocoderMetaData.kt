package com.example.sunrisemoonriseapp.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeocoderMetaData(
    @SerializedName("kind")
    val kind: String
)