package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.openweather

import com.google.gson.annotations.SerializedName

data class CloudsData(
    @SerializedName("all")
    val all: Int,
)
