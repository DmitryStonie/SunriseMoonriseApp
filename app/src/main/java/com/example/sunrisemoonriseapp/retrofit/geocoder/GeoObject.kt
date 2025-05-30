package com.example.sunrisemoonriseapp.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeoObject(
    @SerializedName("metaDataProperty")
    val metaDataProperty: MetaDataProperty,
    @SerializedName("name")
    val name: String
)