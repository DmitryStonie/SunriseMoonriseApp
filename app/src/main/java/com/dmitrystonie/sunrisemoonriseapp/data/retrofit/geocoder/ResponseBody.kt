package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class ResponseBody(
    @SerializedName("GeoObjectCollection")
    val geoObjectCollection: GeoObjectCollection
)