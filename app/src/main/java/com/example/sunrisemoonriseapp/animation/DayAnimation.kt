package com.example.sunrisemoonriseapp.animation

import android.animation.Animator
import android.animation.ValueAnimator


abstract class DayAnimation {
    constructor(state: AnimationState, animators: HashMap<AnimationState, ValueAnimator?>) {
        this.state = state
        for (state in AnimationState.entries) {
            this.animators[state] = animators[state]
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

    var state: AnimationState = AnimationState.DAY
    protected val animators = HashMap<AnimationState, ValueAnimator?>()
    var isCancelled = false
    var animationFraction = 0.0F
    var lastAnimatedValue = 0.0F
    protected var currentAnimator: ValueAnimator? = null

    abstract fun startAnimator()

    fun stopAnimator() {
        if(currentAnimator == null){
            isCancelled = true
        } else {
            currentAnimator?.cancel()
        }

    }

    protected fun startAnimation(animator: ValueAnimator) {
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                isCancelled = false
            }

            override fun onAnimationEnd(p0: Animator) {
                animator.removeAllListeners()
            }

            override fun onAnimationCancel(p0: Animator) {
                animationFraction = animator.animatedFraction
                lastAnimatedValue = animator.animatedValue as? Float ?: (animator.animatedValue as Int).toFloat()
                isCancelled = true
            }

            override fun onAnimationRepeat(p0: Animator) {}

        })
        animator.start()
    }

    fun changeAnimations(currentAnimator: ValueAnimator?, animators: HashMap<AnimationState, ValueAnimator?>) {
        this.currentAnimator = currentAnimator
        for (state in AnimationState.entries) {
            this.animators[state] = animators[state]
        }
    }

}