package com.example.sunrisemoonriseapp

import android.animation.ValueAnimator
import androidx.lifecycle.ViewModel
import com.example.sunrisemoonriseapp.animation.DayAnimation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.sunrisemoonriseapp.MainActivity.Companion.ANIM_DURATION
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.day.Day.Companion.NIGHT_START_START_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.DAWN_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.SUNRISE_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.DAY_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.SOLAR_NOON_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.GOLDEN_HOUR_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.SUNSET_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.DUSK_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.NIGHT_END_START_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.NIGHT_END_END_DEFAULT
import com.example.sunrisemoonriseapp.day.DayState
import com.example.sunrisemoonriseapp.repository.SunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(val repository: SunRepository) : ViewModel() {
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

    lateinit var day: Day
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

    fun getDayInfo(latitude: String, longitude: String) : LiveData<Day?> {
        return liveData {
            val data = repository.getSunrise(latitude, longitude)
            if(data == null){
                null
            }
            val values = arrayListOf(
                NIGHT_START_START_DEFAULT,
                if(data?.dawn != null) data.dawn.getSecondsValue() else DAWN_DEFAULT,
                if(data?.sunrise != null) data.sunrise.getSecondsValue() else SUNRISE_DEFAULT,
                if(data?.sunrise != null) data.sunrise.getSecondsValue() + 240 else DAY_DEFAULT,
                if(data?.solarNoon != null) data.solarNoon.getSecondsValue() else SOLAR_NOON_DEFAULT,
                if(data?.goldenHour != null) data.goldenHour.getSecondsValue() else GOLDEN_HOUR_DEFAULT,
                if(data?.sunset != null) data.sunset.getSecondsValue() else SUNSET_DEFAULT,
                if(data?.sunset != null) data.sunset.getSecondsValue() + 240 else DUSK_DEFAULT,
                if(data?.dusk != null) data.dusk.getSecondsValue() else NIGHT_END_START_DEFAULT,
                NIGHT_END_END_DEFAULT
            )
            day = Day(
                nightStart = DayState(values[0], values[1] ),
                dawn = DayState(values[1], values[2]),
                sunrise = DayState(values[2], values[3]),
                day = DayState(values[3], values[5]),
                solarNoon = DayState(values[4], values[4]),
                goldenHour = DayState(values[5], values[6]),
                sunset = DayState(values[6], values[7]),
                dusk = DayState(values[7], values[8]),
                nightEnd = DayState(values[8], values[9])
            )
            emit(day)
        }
    }
}