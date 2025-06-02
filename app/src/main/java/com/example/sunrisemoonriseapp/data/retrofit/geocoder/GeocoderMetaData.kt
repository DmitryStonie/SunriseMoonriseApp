package com.example.sunrisemoonriseapp.data.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeocoderMetaData(
    @SerializedName("kind")
    val kind: String
)