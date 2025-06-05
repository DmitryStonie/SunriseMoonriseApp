package com.example.sunrisemoonriseapp.data.retrofit.openweather

import com.google.gson.annotations.SerializedName

data class CloudsData(
    @SerializedName("all")
    val all: Int,
)
