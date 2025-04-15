package com.example.sunrisemoonriseapp

import android.animation.ValueAnimator
import androidx.lifecycle.ViewModel
import com.example.sunrisemoonriseapp.animation.DayAnimation
import android.util.Log
import com.example.sunrisemoonriseapp.MainActivity.Companion.ANIM_DURATION


class MainScreenViewModel : ViewModel() {
    var isAnimationPlaying = false
    var state = DayAnimation.Companion.AnimationState.DAY
    var isInitialized = false
    var isCancelled = false
    var isUpdated = false
    var animationCompletionTime = 0L


    var sunAnimator: DayAnimation? = null
    var moonAnimator: DayAnimation? = null
    var sunColorAnimator: DayAnimation? = null
    var moonColorAnimator: DayAnimation? = null
    var skyColorAnimator: DayAnimation? = null
    var groundColorAnimator: DayAnimation? = null

    var animatorsCompleted: Int = 0

    fun initAnimators(
        sunAnimator: DayAnimation,
        moonAnimator: DayAnimation,
        sunColorAnimator: DayAnimation,
        moonColorAnimator: DayAnimation,
        skyColorAnimator: DayAnimation,
        groundColorAnimator: DayAnimation
    ) {
        this.sunAnimator = sunAnimator
        this.moonAnimator = moonAnimator
        this.sunColorAnimator = sunColorAnimator
        this.moonColorAnimator = moonColorAnimator
        this.skyColorAnimator = skyColorAnimator
        this.groundColorAnimator = groundColorAnimator
        this.sunAnimator?.state = state
        this.moonAnimator?.state = state
        this.sunColorAnimator?.state = state
        this.moonColorAnimator?.state = state
        this.skyColorAnimator?.state = state
        this.groundColorAnimator?.state = state
        this.sunAnimator!!.onCompleteCallBack = ::onAnimatorComplete
        this.moonAnimator!!.onCompleteCallBack = ::onAnimatorComplete
        this.sunColorAnimator!!.onCompleteCallBack = ::onAnimatorComplete
        this.moonColorAnimator!!.onCompleteCallBack = ::onAnimatorComplete
        this.skyColorAnimator!!.onCompleteCallBack = ::onAnimatorComplete
        this.groundColorAnimator!!.onCompleteCallBack = ::onAnimatorComplete
        isInitialized = true
    }

    fun startAnimators() {
        sunAnimator?.isCompleted = false
        moonAnimator?.isCompleted = false
        sunColorAnimator?.isCompleted = false
        moonColorAnimator?.isCompleted = false
        skyColorAnimator?.isCompleted = false
        groundColorAnimator?.isCompleted = false
        continueAnimators()
    }
    fun continueAnimators() {
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
    }

    fun stopAnimators() {
        sunAnimator?.stopAnimator()
        moonAnimator?.stopAnimator()
        sunColorAnimator?.stopAnimator()
        moonColorAnimator?.stopAnimator()
        skyColorAnimator?.stopAnimator()
        groundColorAnimator?.stopAnimator()
        isCancelled = true
        isUpdated = false
        isAnimationPlaying = false
        animationCompletionTime = listOf(
            (ANIM_DURATION * (1 - (sunAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (moonAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (sunColorAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (moonColorAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (skyColorAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (groundColorAnimator?.animationCompletion ?: 0.0F)))
        ).max().toLong()
    }

    fun onAnimatorComplete(animator: ValueAnimator?) {
        animatorsCompleted += 1
        Log.d("ViewModel", "Animators completed ${animatorsCompleted}   animator  $animator")
        if(animatorsCompleted == 6){
            finishAnimations()
        }
    }

    fun finishAnimations(){
        animatorsCompleted = 0
        isAnimationPlaying = false
        isCancelled = false
        state = DayAnimation.Companion.nextState(state)
        Log.d("ViewModel", "MainScreenViewModel changed state to ${state}")
    }

}