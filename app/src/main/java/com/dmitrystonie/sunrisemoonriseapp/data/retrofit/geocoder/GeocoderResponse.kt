package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeocoderResponse(
    @SerializedName("response")
    val response: ResponseBody
)