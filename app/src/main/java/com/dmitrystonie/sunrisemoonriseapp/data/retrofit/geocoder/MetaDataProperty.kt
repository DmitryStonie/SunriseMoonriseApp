package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class MetaDataProperty(
    @SerializedName("GeocoderMetaData")
    val geocoderMetaData: GeocoderMetaData
)