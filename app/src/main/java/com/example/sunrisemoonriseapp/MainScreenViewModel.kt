package com.example.sunrisemoonriseapp

import androidx.lifecycle.ViewModel
import com.example.sunrisemoonriseapp.animation.DayAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.cancel


class MainScreenViewModel: ViewModel() {
    var isAnimationPlaying = false
    var state = DayAnimation.Companion.AnimationState.DAY
    var isInitialized = false
    var isCancelled = false
    var isUpdated = false

    var sunAnimator: DayAnimation? = null
    var moonAnimator: DayAnimation? = null
    var sunColorAnimator: DayAnimation? = null
    var moonColorAnimator: DayAnimation? = null
    var skyColorAnimator: DayAnimation? = null
    var groundColorAnimator: DayAnimation? = null

    lateinit var scope: CoroutineScope

    fun initAnimators(sunAnimator: DayAnimation, moonAnimator: DayAnimation, sunColorAnimator: DayAnimation, moonColorAnimator: DayAnimation, skyColorAnimator: DayAnimation, groundColorAnimator: DayAnimation){
        this.sunAnimator = sunAnimator
        this.moonAnimator = moonAnimator
        this.sunColorAnimator = sunColorAnimator
        this.moonColorAnimator = moonColorAnimator
        this.skyColorAnimator = skyColorAnimator
        this.groundColorAnimator = groundColorAnimator
        isInitialized = true
    }

    fun startAnimators() {
        scope = CoroutineScope(Dispatchers.Default)
        Log.d("ViewModel", "MainScreenViewModel started animations from state")
        isAnimationPlaying = true
        isCancelled = false
        sunAnimator?.state = state
        moonAnimator?.state = state
        sunColorAnimator?.state = state
        moonColorAnimator?.state = state
        skyColorAnimator?.state = state
        groundColorAnimator?.state = state
        sunAnimator?.startAnimator()
        moonAnimator?.startAnimator()
        sunColorAnimator?.startAnimator()
        moonColorAnimator?.startAnimator()
        skyColorAnimator?.startAnimator()
        groundColorAnimator?.startAnimator()

        scope.launch {
            delay(MainActivity.Companion.ANIM_DURATION)
            isAnimationPlaying = false
            isCancelled = false
            state = DayAnimation.Companion.nextState(state)
            Log.d("ViewModel", "MainScreenViewModel changed state to ${state}")
        }
    }
    fun stopAnimators() {
        sunAnimator?.stopAnimator()
        moonAnimator?.stopAnimator()
        sunColorAnimator?.stopAnimator()
        moonColorAnimator?.stopAnimator()
        skyColorAnimator?.stopAnimator()
        groundColorAnimator?.stopAnimator()
        scope.cancel()
        isCancelled = true
        isUpdated = false
        isAnimationPlaying = false
    }


}