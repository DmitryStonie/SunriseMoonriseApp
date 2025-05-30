package com.example.sunrisemoonriseapp.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember")
    val featureMember: List<FeatureMember>
)