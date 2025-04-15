package com.example.sunrisemoonriseapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.AnimationState
import com.example.sunrisemoonriseapp.animation.DayColorAnimation
import com.example.sunrisemoonriseapp.animation.DayMovementAnimation


class MainActivity : AppCompatActivity() {


    private lateinit var sunView: ImageView
    private lateinit var moonView: View
    private lateinit var skyView: View
    private lateinit var groundView: View
    private lateinit var sceneView: View

    private lateinit var viewModel: MainScreenViewModel

    private val skySunriseColor: Int by lazy {
        getColor(R.color.skysunrise)
    }
    private val skyDayColor: Int by lazy {
        getColor(R.color.skyday)
    }
    private val skySunsetColor: Int by lazy {
        getColor(R.color.skysunset)
    }
    private val skyNightColor: Int by lazy {
        getColor(R.color.skynight)
    }
    private val sunriseColor: Int by lazy {
        getColor(R.color.sunrise)
    }
    private val sundayColor: Int by lazy {
        getColor(R.color.sunday)
    }
    private val sunsetColor: Int by lazy {
        getColor(R.color.sunset)
    }
    private val moonColor: Int by lazy {
        getColor(R.color.moon)
    }
    private val moonColorBright: Int by lazy {
        getColor(R.color.moonBright)
    }
    private val groundColor: Int by lazy {
        getColor(R.color.groundday)
    }
    private val groundColorNight: Int by lazy {
        getColor(R.color.groundnight)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "MAinActivity successfully created $this")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val provider = ViewModelProvider(this)
        viewModel = provider[MainScreenViewModel::class.java]

        sunView = findViewById(R.id.sun)
        moonView = findViewById(R.id.moon)
        skyView = findViewById(R.id.sky)
        groundView = findViewById(R.id.ground)
        sceneView = findViewById(R.id.scene)

        sceneView.setOnClickListener {
            if (!viewModel.isAnimationPlaying) {
                viewModel.startAnimators()
            }
        }

        sceneView.viewTreeObserver.addOnGlobalLayoutListener(
            OnGlobalLayoutListener {
                if (!viewModel.isInitialized) {
                    initAnimations()
                    Log.d(LOG_TAG, "MainActivity initialized ViewModel")
                } else if(!viewModel.isUpdated){
                    updateAnimations()
                    Log.d(LOG_TAG, "MainActivity updated ViewModel")
                }
                if(viewModel.isCancelled){
                    viewModel.startAnimators()
                    Log.d(LOG_TAG, "MainActivity started animations")
                } else{
                    updateElements()
                    Log.d(LOG_TAG, "MainActivity updated elements")

                }
            }
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "MainActivity changed orientation")
        if (viewModel.isAnimationPlaying) {
            viewModel.stopAnimators()
            Log.d(LOG_TAG, "MainActivity stopped animators")
        }
    }

    private fun initAnimations(){
        viewModel.initAnimators(
            DayMovementAnimation(
                viewModel.state,
                getSunAnimations()
            ),
            DayMovementAnimation(
                viewModel.state,
                getMoonAnimations()
            ),
            DayColorAnimation(
                viewModel.state,
                getSunColorAnimations()
            ),
            DayColorAnimation(
                viewModel.state,
                getMoonColorAnimations()
            ),
            DayColorAnimation(
                viewModel.state,
                getSkyColorAnimations()
            ),
            DayColorAnimation(
                viewModel.state,
                getGroundColorAnimations()
            )
        )
    }

    private fun updateAnimations(){
        viewModel.sunAnimator?.changeAnimations(
            getSunAnimator(
                viewModel.state,
                viewModel.sunAnimator?.animationFraction ?: 0.0F
            ), getSunAnimations()
        )
        viewModel.moonAnimator?.changeAnimations(
            getMoonAnimator(
                viewModel.state,
                viewModel.moonAnimator?.animationFraction ?: 0.0F
            ), getMoonAnimations()
        )
        viewModel.sunColorAnimator?.changeAnimations(
            getSunColorAnimator(viewModel.state), getSunColorAnimations()
        )
        viewModel.moonColorAnimator?.changeAnimations(
            getMoonColorAnimator(viewModel.state), getMoonColorAnimations()
        )
        viewModel.skyColorAnimator?.changeAnimations(
            getSkyColorAnimator(viewModel.state), getSkyColorAnimations()
        )
        viewModel.groundColorAnimator?.changeAnimations(
            getGroundColorAnimator(viewModel.state), getGroundColorAnimations()
        )
        viewModel.isUpdated = true
    }

    private fun updateElements(){
        when(viewModel.state){
            AnimationState.DAY -> {
                sunView.y = getSunPosition(AnimationState.DAY)
                moonView.y = getMoonPosition(AnimationState.DAY)
                (sunView.background as GradientDrawable).setColor(sundayColor)
                skyView.setBackgroundColor(skyDayColor)
                groundView.setBackgroundColor(groundColor)
            }
            AnimationState.SUNSET -> {
                sunView.y = getSunPosition(AnimationState.SUNSET)
                moonView.y = getMoonPosition(AnimationState.SUNSET)
                (sunView.background as GradientDrawable).setColor(sunsetColor)
                skyView.setBackgroundColor(skySunsetColor)
                groundView.setBackgroundColor(groundColor)
            }
            AnimationState.NIGHT -> {
                sunView.y = getSunPosition(AnimationState.NIGHT)
                moonView.y = getMoonPosition(AnimationState.NIGHT)
                (moonView.background as GradientDrawable).setColor(moonColorBright)
                skyView.setBackgroundColor(skyNightColor)
                groundView.setBackgroundColor(groundColorNight)

            }
            AnimationState.SUNRISE -> {
                sunView.y = getSunPosition(AnimationState.SUNRISE)
                moonView.y = getMoonPosition(AnimationState.SUNRISE)
                (sunView.background as GradientDrawable).setColor(sunriseColor)
                skyView.setBackgroundColor(skySunriseColor)
                groundView.setBackgroundColor(groundColor)


            }
        }
    }

    private fun getSunPosition(state: AnimationState): Float {
        return when (state) {
            AnimationState.DAY -> {
                skyView.height.toFloat() / 2 - sunView.height.toFloat() / 2
            }

            AnimationState.SUNSET, AnimationState.SUNRISE -> {
                sceneView.height.toFloat() - groundView.height.toFloat() - sunView.height / 2
            }

            AnimationState.NIGHT -> {
                sceneView.height.toFloat() - groundView.height.toFloat()
            }
        }
    }

    private fun getMoonPosition(state: AnimationState): Float {
        return when (state) {
            AnimationState.DAY, AnimationState.SUNSET, AnimationState.SUNRISE -> {
                sceneView.height.toFloat() - moonView.height.toFloat()
            }

            AnimationState.NIGHT -> {
                skyView.height.toFloat() / 3 - moonView.height.toFloat() / 2
            }

        }
    }

    fun getMovementAnimator(
        view: View,
        startPos: Float,
        endPos: Float,
        animDuration: Long = ANIM_DURATION
    ): ValueAnimator {
        return ValueAnimator.ofFloat(
            startPos, endPos
        )
            .apply {
                interpolator = AccelerateInterpolator()
                duration = animDuration
                addUpdateListener { animator ->
                    view.y = animator.animatedValue as Float
                }
            }

        return ObjectAnimator.ofFloat(view, "y", startPos, endPos).apply {
            duration = animDuration
            interpolator = AccelerateInterpolator()
        }
    }

    private fun getViewColorAnimator(
        view: View,
        startColor: Int,
        endColor: Int,
        animDuration: Long = ANIM_DURATION
    ): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(),
            startColor,
            endColor
        ).apply {
            duration = animDuration
            addUpdateListener { animator ->
                view.setBackgroundColor(
                    animator.animatedValue as Int
                )
            }
        }
    }

    private fun getDrawableColorAnimator(
        view: View,
        startColor: Int,
        endColor: Int,
        animDuration: Long = ANIM_DURATION
    ): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(),
            startColor,
            endColor
        ).apply {
            duration = animDuration
            addUpdateListener { animator ->
                (view.background as GradientDrawable).setColor(
                    animator.animatedValue as Int
                )
            }
        }
    }

    private fun getSunAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
            result[state] = getSunAnimator(state, animationFraction)
        }
        return result
    }

    private fun getMoonAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
            result[state] = getMoonAnimator(state, animationFraction)
        }
        return result
    }

    private fun getSunColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
            result[state] = getSunColorAnimator(state, animationFraction)
        }
        return result
    }

    private fun getMoonColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
            result[state] = getMoonColorAnimator(state, animationFraction)
        }
        return result
    }

    private fun getSkyColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
            result[state] = getSkyColorAnimator(state, animationFraction)
        }
        return result
    }

    private fun getGroundColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
            result[state] = getGroundColorAnimator(state, animationFraction)
        }
        return result
    }

    private fun getSunAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            AnimationState.DAY -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(AnimationState.DAY) + animationFraction * (getSunPosition(
                        AnimationState.SUNSET
                    ) - getSunPosition(AnimationState.DAY)),
                    getSunPosition(AnimationState.SUNSET),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            AnimationState.SUNSET -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(AnimationState.SUNSET) + animationFraction * (getSunPosition(
                        AnimationState.NIGHT
                    ) - getSunPosition(AnimationState.SUNSET)),
                    getSunPosition(AnimationState.NIGHT),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            AnimationState.NIGHT -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(AnimationState.NIGHT) + animationFraction * (getSunPosition(
                        AnimationState.SUNRISE
                    ) - getSunPosition(AnimationState.NIGHT)),
                    getSunPosition(AnimationState.SUNRISE),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            AnimationState.SUNRISE -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(AnimationState.SUNRISE) + animationFraction * (getSunPosition(
                        AnimationState.DAY
                    ) - getSunPosition(AnimationState.SUNRISE)),
                    getSunPosition(AnimationState.DAY),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }
        }
    }

    private fun getMoonAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            AnimationState.DAY -> {
                null
            }

            AnimationState.SUNSET -> {
                getMovementAnimator(
                    moonView,
                    getMoonPosition(AnimationState.SUNSET) + animationFraction * (getMoonPosition(
                        AnimationState.NIGHT
                    ) - getMoonPosition(AnimationState.SUNSET)),
                    getMoonPosition(AnimationState.NIGHT)
                )
            }

            AnimationState.NIGHT -> {
                getMovementAnimator(
                    moonView,
                    getMoonPosition(AnimationState.NIGHT) + animationFraction * (getMoonPosition(
                        AnimationState.SUNRISE
                    ) - getMoonPosition(AnimationState.NIGHT)),
                    getMoonPosition(AnimationState.SUNRISE)
                )
            }

            AnimationState.SUNRISE -> {
                null
            }
        }
    }

    private fun getSunColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            AnimationState.DAY -> {
                getDrawableColorAnimator(
                    sunView,
                    sundayColor,
                    sunsetColor
                )
            }

            AnimationState.SUNSET -> {
                null
            }

            AnimationState.NIGHT -> {
                getDrawableColorAnimator(
                    sunView,
                    sunsetColor,
                    sunriseColor
                )
            }

            AnimationState.SUNRISE -> {
                getDrawableColorAnimator(
                    sunView,
                    sunriseColor,
                    sundayColor
                )
            }
        }
    }

    private fun getMoonColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            AnimationState.DAY -> {
                null
            }

            AnimationState.SUNSET -> {
                getDrawableColorAnimator(
                    moonView,
                    moonColor,
                    moonColorBright
                )
            }

            AnimationState.NIGHT -> {
                getDrawableColorAnimator(
                    moonView,
                    moonColorBright,
                    moonColor
                )
            }

            AnimationState.SUNRISE -> {
                null
            }
        }
    }

    private fun getSkyColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            AnimationState.DAY -> {
                getViewColorAnimator(
                    skyView,
                    skyDayColor,
                    skySunsetColor
                )
            }

            AnimationState.SUNSET -> {
                getViewColorAnimator(
                    skyView,
                    skySunsetColor,
                    skyNightColor
                )
            }

            AnimationState.NIGHT -> {
                getViewColorAnimator(
                    skyView,
                    skyNightColor,
                    skySunriseColor
                )
            }

            AnimationState.SUNRISE -> {
                getViewColorAnimator(
                    skyView,
                    skySunriseColor,
                    skyDayColor
                )
            }
        }
    }

    private fun getGroundColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            AnimationState.DAY -> {
                null
            }

            AnimationState.SUNSET -> {
                getViewColorAnimator(
                    groundView,
                    groundColor,
                    groundColorNight
                )
            }

            AnimationState.NIGHT -> {
                getViewColorAnimator(
                    groundView,
                    groundColorNight,
                    groundColor
                )
            }

            AnimationState.SUNRISE -> {
                null
            }
        }
    }

    companion object {
        const val ANIM_DURATION = 2000L
//        private const val ANIM_DURATION_SHORT = 1000L
        private val LOG_TAG = "MainActivityTag"
    }
}
