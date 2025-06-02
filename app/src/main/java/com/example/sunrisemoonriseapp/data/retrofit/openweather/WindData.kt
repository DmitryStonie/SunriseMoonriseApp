package com.example.sunrisemoonriseapp.data.retrofit.openweather

import com.google.gson.annotations.SerializedName

data class WindData(
    @SerializedName("speed")
    val speed: Float,
    @SerializedName("deg")
    val deg: Int,
)
