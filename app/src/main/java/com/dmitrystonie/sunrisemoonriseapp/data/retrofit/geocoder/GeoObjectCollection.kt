package com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder

import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember")
    val featureMember: List<FeatureMember>
)