package com.example.sunrisemoonriseapp.entities


data class Moon (
    val date: String,
    val moon: String,
    val index: Int,
    val age: Double,
    val phase: String,
    val distance: Float,
    val illumination: Double,
    val angularDiameter: Double,
    val distanceToSun: Double,
    val sunAngularDiameter: Double
) {
    companion object{
        const val FULL_MOON_AGE = 14.7F
    }
}