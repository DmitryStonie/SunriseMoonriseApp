package com.example.sunrisemoonriseapp.data.retrofit.moon

import com.google.gson.annotations.SerializedName

data class MoonDate (
    @SerializedName(value="Error")
    val error: Int,
    @SerializedName(value="ErrorMsg")
    val errorMsg: String,
    @SerializedName(value="TargetDate")
    val targetDate: String,
    @SerializedName(value="Moon")
    val moon: List<String>,
    @SerializedName(value="Index")
    val index: Int,
    @SerializedName(value="Age")
    val age: Double,
    @SerializedName(value="Phase")
    val phase: String,
    @SerializedName(value="Distance")
    val distance: Float,
    @SerializedName(value="Illumination")
    val illumination: Double,
    @SerializedName(value="AngularDiameter")
    val angularDiameter: Double,
    @SerializedName(value="DistanceToSun")
    val distanceToSun: Double,
    @SerializedName(value="SunAngularDiameter")
    val sunAngularDiameter: Double

)