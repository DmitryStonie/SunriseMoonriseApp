package com.example.sunrisemoonriseapp.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log


open class DayAnimation {
    constructor(animators: HashMap<AnimationState, ValueAnimator?>) {
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
    }

    protected val animators = HashMap<AnimationState, ValueAnimator?>()
    var isCancelled = false
    var isCompleted = false
    var animationFraction = 0.0F
    var animationCompletion = 0.0F
    var lastAnimatedValue = 0.0F
    var state: AnimationState = AnimationState.DAY
    protected var currentAnimator: ValueAnimator? = null
    lateinit var onCompleteCallBack: (ValueAnimator?) -> Unit

    fun startAnimator() {
        if(isCancelled){
            isCancelled = false
            if (currentAnimator != null) {
                Log.d("ViewModel", "Animator started $currentAnimator from cancelled")
                startAnimation(currentAnimator!!)
            }
        } else { // just start or start if it's already completed
            currentAnimator = animators[state]
            if(!isCompleted){
                if (currentAnimator != null) {
                    Log.d("ViewModel", "Animator started $currentAnimator from completed")
                    startAnimation(currentAnimator!!)
                } else {
                    isCompleted = true
                    onCompleteCallBack(currentAnimator)
                }
            }
        }


    }

    fun stopAnimator() {
        if(currentAnimator == null){
            isCancelled = true
        } else {
            currentAnimator?.cancel()
        }
    }


    fun startAnimation(animator: ValueAnimator) {
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                animator.removeAllListeners()
                animationFraction = animator.animatedFraction
                if(isCancelled){
                    if(animationCompletion > 0.01F && animationCompletion < 0.99F){
                        animationCompletion = animationCompletion + (1.0F - animationCompletion) * animationFraction
                    } else if(animationCompletion <= 0.01F){
                        animationCompletion = animationFraction
                    }
                } else {
                    isCompleted = true
                    onCompleteCallBack(currentAnimator)
                    animationCompletion = 0.0F
                }
            }

            override fun onAnimationCancel(p0: Animator) {
                lastAnimatedValue = animator.animatedValue as? Float ?: (animator.animatedValue as Int).toFloat()
                isCancelled = true
                Log.d("ViewModel", "Animator cancelled $animator")
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