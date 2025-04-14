package com.example.sunrisemoonriseapp

import androidx.lifecycle.ViewModel
import kotlin.collections.plusAssign

class MainScreenViewModel: ViewModel() {
    var currentState = 1
    var currentSunPosition: Float = 0.0F
    var currentMoonPosition: Float = 0.0F
    var currentSunEndPosition: Float = 0.0F
    var currentMoonEndPosition: Float = 0.0F

    var isAnimationPlaying = false


    fun updateCurrentState() {
        currentState += 1
        if (currentState == 5) {
            currentState = 1
        }
    }

}