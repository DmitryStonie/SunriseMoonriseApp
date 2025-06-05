package com.example.sunrisemoonriseapp.data.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeocoderResponse(
    @SerializedName("response")
    val response: ResponseBody
)