package com.example.sunrisemoonriseapp.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log


class DayAnimation {
    constructor(state: AnimationState, vararg animators: ValueAnimator?) {
        this.state = state
        for (i in 0..<AnimationState.entries.size) {
            this.animators[AnimationState.entries[i]] = animators[i]
        }
    }

    companion object {
        enum class AnimationState() {
            DAY, SUNSET, NIGHT, SUNRISE
        }

        fun nextState(state: AnimationState): AnimationState {
            return when (state) {
                AnimationState.DAY -> AnimationState.SUNSET
                AnimationState.SUNSET -> AnimationState.NIGHT
                AnimationState.NIGHT -> AnimationState.SUNRISE
                AnimationState.SUNRISE -> AnimationState.DAY
            }
        }
        fun prevState(state: AnimationState): AnimationState {
            return when (state) {
                AnimationState.DAY -> AnimationState.SUNRISE
                AnimationState.SUNSET -> AnimationState.DAY
                AnimationState.NIGHT -> AnimationState.SUNSET
                AnimationState.SUNRISE -> AnimationState.NIGHT
            }
        }
    }

    private var isRunning = false
    var state: AnimationState = AnimationState.DAY
    private val animators = HashMap<AnimationState, ValueAnimator?>()
    var isCancelled = false
    var animationFraction = 0.0F
    var lastAnimatedValue = 0.0F

    fun startAnimator() {
        if (animators[state] == null) {
            isRunning = false
            state = nextState(state)
            Log.d("DayAnimation", "DayAnimation $this is started and changed state to $state")
        } else {
            Log.d("DayAnimation", "DayAnimation $this is started")
            startAnimation(animators[state]!!)
        }
    }

    fun stopAnimator() {
        if(isRunning == false){
            isCancelled = true
            state = prevState(state)
            Log.d("DayAnimation", "DayAnimation $this is started and changed state back to $state")
        } else if (animators[state] != null) {
            animators[state]?.cancel()
        }

    }

    private fun startAnimation(animator: ValueAnimator) {
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                isRunning = true
                isCancelled = false
            }

            override fun onAnimationEnd(p0: Animator) {
                isRunning = false
                animator.removeAllListeners()
                if (!isCancelled) {
                    state = nextState(state)
                    Log.d("DayAnimation", "DayAnimation changed state to ${state}")
                }
                Log.d("DayAnimation", "DayAnimation $this ended")
            }

            override fun onAnimationCancel(p0: Animator) {
                Log.d("DayAnimation", "DayAnimation $this cancelled")
                animationFraction = animator.animatedFraction
                lastAnimatedValue = animator.animatedValue as? Float ?: (animator.animatedValue as Int).toFloat()
                isCancelled = true
            }

            override fun onAnimationRepeat(p0: Animator) {}

        })
        animator.start()
    }

    fun changeAnimations(vararg animators: ValueAnimator?) {
        for (i in 0..<AnimationState.entries.size) {
            this.animators[AnimationState.entries[i]] = animators[i]
        }
    }

}