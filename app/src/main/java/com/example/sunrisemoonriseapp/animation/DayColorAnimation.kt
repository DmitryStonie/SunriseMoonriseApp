package com.example.sunrisemoonriseapp.animation

import android.animation.ValueAnimator

class DayColorAnimation(state: Companion.AnimationState,
                        animators: HashMap<Companion.AnimationState, ValueAnimator?>
) : DayAnimation(state, animators) {
    override fun startAnimator() {
        currentAnimator = animators[state]
        isCancelled = false
        if (currentAnimator != null) {
            startAnimation(currentAnimator!!)
        }
    }

}