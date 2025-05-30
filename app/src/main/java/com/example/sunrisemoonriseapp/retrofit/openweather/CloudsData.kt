package com.example.sunrisemoonriseapp.retrofit.openweather

import com.google.gson.annotations.SerializedName

data class CloudsData(
    @SerializedName("all")
    val all: Int,
)
