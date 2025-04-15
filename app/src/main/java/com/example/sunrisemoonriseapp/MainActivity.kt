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
import com.example.sunrisemoonriseapp.animation.DayAnimation
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.AnimationState
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.AnimationState.*
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.nextState


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
                } else if (!viewModel.isUpdated) {
                    updateAnimations()
                    Log.d(LOG_TAG, "MainActivity updated ViewModel")
                }
                if (viewModel.isCancelled) {
                    viewModel.continueAnimators()
                    Log.d(LOG_TAG, "MainActivity started animations")
                } else {
                    updateElements()
                    Log.d(LOG_TAG, "MainActivity updated elements")
                }
            }
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "MainActivity changed orientation")
        viewModel.isUpdated = false
        if (viewModel.isAnimationPlaying) {
            viewModel.stopAnimators()
            Log.d(LOG_TAG, "MainActivity stopped animators")
        }
    }

    private fun initAnimations() {
        viewModel.initAnimators(
            DayAnimation(
                getSunAnimations()
            ),
            DayAnimation(
                getMoonAnimations()
            ),
            DayAnimation(
                getSunColorAnimations()
            ),
            DayAnimation(
                getMoonColorAnimations()
            ),
            DayAnimation(
                getSkyColorAnimations()
            ),
            DayAnimation(
                getGroundColorAnimations()
            )
        )
        viewModel.animationCompletionTime = ANIM_DURATION
    }

    private fun updateAnimations() {
        Log.d("Completion", "Completion ${viewModel.sunAnimator?.animationCompletion}")
        viewModel.sunAnimator?.changeAnimations(
            getSunAnimator(
                viewModel.state,
                viewModel.sunAnimator?.animationCompletion ?: 0.0F
            ), getSunAnimations()
        )
        viewModel.moonAnimator?.changeAnimations(
            getMoonAnimator(
                viewModel.state,
                viewModel.moonAnimator?.animationCompletion ?: 0.0F
            ), getMoonAnimations()
        )
        viewModel.sunColorAnimator?.changeAnimations(
            getSunColorAnimator(
                viewModel.state,
                viewModel.sunColorAnimator?.animationCompletion ?: 0.0F
            ),
            getSunColorAnimations(),
        )
        viewModel.moonColorAnimator?.changeAnimations(
            getMoonColorAnimator(
                viewModel.state,
                viewModel.moonColorAnimator?.animationCompletion ?: 0.0F
            ), getMoonColorAnimations()
        )
        viewModel.skyColorAnimator?.changeAnimations(
            getSkyColorAnimator(
                viewModel.state,
                viewModel.skyColorAnimator?.animationCompletion ?: 0.0F
            ), getSkyColorAnimations()
        )
        viewModel.groundColorAnimator?.changeAnimations(
            getGroundColorAnimator(
                viewModel.state,
                viewModel.groundColorAnimator?.animationCompletion ?: 0.0F
            ), getGroundColorAnimations()
        )
        viewModel.isUpdated = true
        viewModel.animationCompletionTime = listOf(
            (ANIM_DURATION * (1 - (viewModel.sunAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (viewModel.moonAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (viewModel.sunColorAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (viewModel.moonColorAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (viewModel.skyColorAnimator?.animationCompletion ?: 0.0F))),
            (ANIM_DURATION * (1 - (viewModel.groundColorAnimator?.animationCompletion ?: 0.0F)))
        ).max().toLong()
    }

    private fun updateSunColor(state: AnimationState){
        when(state){
            DAY -> {
                (sunView.background as GradientDrawable).setColor(sundayColor)
            }
            SUNSET -> {
                (sunView.background as GradientDrawable).setColor(sunsetColor)
            }
            SUNRISE -> {
                (sunView.background as GradientDrawable).setColor(sunriseColor)
            }
            else -> {}
        }
    }
    private fun updateSunPosition(state: AnimationState){
        when(state){
            DAY -> {
                sunView.y = getSunPosition(DAY)

            }
            SUNSET -> {
                sunView.y = getSunPosition(SUNSET)

            }
            NIGHT -> {
                sunView.y = getSunPosition(NIGHT)

            }
            SUNRISE -> {
                sunView.y = getSunPosition(SUNRISE)

            }
        }
    }
    private fun updateMoonPosition(state: AnimationState){
        when(state){
            DAY -> {
                moonView.y = getMoonPosition(DAY)

            }
            SUNSET -> {
                moonView.y = getMoonPosition(SUNSET)

            }
            NIGHT -> {
                moonView.y = getMoonPosition(NIGHT)

            }
            SUNRISE -> {
                moonView.y = getMoonPosition(SUNRISE)

            }
        }
    }
    private fun updateMoonColor(state: AnimationState){
        when(state){
            NIGHT -> {
                (moonView.background as GradientDrawable).setColor(moonColorBright)

            }
            else -> {}
        }
    }
    private fun updateSkyColor(state: AnimationState){
        when(state){
            DAY -> {
                skyView.setBackgroundColor(skyDayColor)

            }
            SUNSET -> {
                skyView.setBackgroundColor(skySunsetColor)

            }
            NIGHT -> {
                skyView.setBackgroundColor(skyNightColor)

            }
            SUNRISE -> {
                skyView.setBackgroundColor(skySunriseColor)

            }
        }
    }
    private fun updateGroundColor(state: AnimationState){
        when(state){
            DAY -> {
                groundView.setBackgroundColor(groundColor)

            }
            SUNSET -> {
                groundView.setBackgroundColor(groundColor)

            }
            NIGHT -> {
                groundView.setBackgroundColor(groundColorNight)

            }
            SUNRISE -> {
                groundView.setBackgroundColor(groundColor)

            }
        }
    }

    private fun updateElements() {
        if(viewModel.sunColorAnimator?.isCompleted == true){
            updateSunColor(nextState(viewModel.state))
        }
        if(viewModel.sunAnimator?.isCompleted == true){
            updateSunPosition(nextState(viewModel.state))
        }
        if(viewModel.moonColorAnimator?.isCompleted == true){
            updateMoonColor(nextState(viewModel.state))
        }
        if(viewModel.moonAnimator?.isCompleted == true){
            updateMoonPosition(nextState(viewModel.state))
        }
        if(viewModel.skyColorAnimator?.isCompleted == true){
            updateSkyColor(nextState(viewModel.state))
        }
        if(viewModel.groundColorAnimator?.isCompleted == true){
            updateGroundColor(nextState(viewModel.state))
        }
    }

    private fun getSunPosition(state: AnimationState): Float {
        return when (state) {
            DAY -> {
                skyView.height.toFloat() / 2 - sunView.height.toFloat() / 2
            }

            SUNSET, SUNRISE -> {
                sceneView.height.toFloat() - groundView.height.toFloat() - sunView.height / 2
            }

            NIGHT -> {
                sceneView.height.toFloat() - groundView.height.toFloat()
            }
        }
    }

    private fun getMoonPosition(state: AnimationState): Float {
        return when (state) {
            DAY, SUNSET, SUNRISE -> {
                sceneView.height.toFloat() - moonView.height.toFloat()
            }

            NIGHT -> {
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
            DAY -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(DAY) + animationFraction * (getSunPosition(
                        SUNSET
                    ) - getSunPosition(DAY)),
                    getSunPosition(SUNSET),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNSET -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(SUNSET) + animationFraction * (getSunPosition(
                        NIGHT
                    ) - getSunPosition(SUNSET)),
                    getSunPosition(NIGHT),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            NIGHT -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(NIGHT) + animationFraction * (getSunPosition(
                        SUNRISE
                    ) - getSunPosition(NIGHT)),
                    getSunPosition(SUNRISE),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNRISE -> {
                getMovementAnimator(
                    sunView,
                    getSunPosition(SUNRISE) + animationFraction * (getSunPosition(
                        DAY
                    ) - getSunPosition(SUNRISE)),
                    getSunPosition(DAY),
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
            DAY -> {
                null
            }

            SUNSET -> {
                getMovementAnimator(
                    moonView,
                    getMoonPosition(SUNSET) + animationFraction * (getMoonPosition(
                        NIGHT
                    ) - getMoonPosition(SUNSET)),
                    getMoonPosition(NIGHT),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            NIGHT -> {
                getMovementAnimator(
                    moonView,
                    getMoonPosition(NIGHT) + animationFraction * (getMoonPosition(
                        SUNRISE
                    ) - getMoonPosition(NIGHT)),
                    getMoonPosition(SUNRISE),
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNRISE -> {
                null
            }
        }
    }

    private fun getSunColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            DAY -> {
                getDrawableColorAnimator(
                    sunView,
                    sundayColor,
                    sunsetColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNSET -> {
                null
            }

            NIGHT -> {
                getDrawableColorAnimator(
                    sunView,
                    sunsetColor,
                    sunriseColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNRISE -> {
                getDrawableColorAnimator(
                    sunView,
                    sunriseColor,
                    sundayColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }
        }
    }

    private fun getMoonColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            DAY -> {
                null
            }

            SUNSET -> {
                getDrawableColorAnimator(
                    moonView,
                    moonColor,
                    moonColorBright,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            NIGHT -> {
                getDrawableColorAnimator(
                    moonView,
                    moonColorBright,
                    moonColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNRISE -> {
                null
            }
        }
    }

    private fun getSkyColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            DAY -> {
                getViewColorAnimator(
                    skyView,
                    skyDayColor,
                    skySunsetColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNSET -> {
                getViewColorAnimator(
                    skyView,
                    skySunsetColor,
                    skyNightColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            NIGHT -> {
                getViewColorAnimator(
                    skyView,
                    skyNightColor,
                    skySunriseColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNRISE -> {
                getViewColorAnimator(
                    skyView,
                    skySunriseColor,
                    skyDayColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }
        }
    }

    private fun getGroundColorAnimator(
        animationsState: AnimationState,
        animationFraction: Float = 0.0F
    ): ValueAnimator? {
        return when (animationsState) {
            DAY -> {
                null
            }

            SUNSET -> {
                getViewColorAnimator(
                    groundView,
                    groundColor,
                    groundColorNight,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            NIGHT -> {
                getViewColorAnimator(
                    groundView,
                    groundColorNight,
                    groundColor,
                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
                )
            }

            SUNRISE -> {
                null
            }
        }
    }

    companion object {
        const val ANIM_DURATION = 2000L
        private const val LOG_TAG = "MainActivityTag"
    }
}
