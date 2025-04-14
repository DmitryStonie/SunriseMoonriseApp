package com.example.sunrisemoonriseapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var sunView: ImageView
    private lateinit var moonView: View
    private lateinit var skyView: View
    private lateinit var groundView: View
    private lateinit var sceneView: View

    private lateinit var viewModel: MainScreenViewModel

    private var sunAnimator: ObjectAnimator? = null
    private var sunColorAnimator: ValueAnimator? = null
    private var moonAnimator: ObjectAnimator? = null
    private var moonColorAnimator: ValueAnimator? = null
    private var skyColorAnimator: ObjectAnimator? = null
    private var groundColorAnimator: ObjectAnimator? = null

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
        Log.d("MainActivity", "MAinActivity successfully created $this")
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

        viewModel.isAnimationPlaying = false

        sceneView.setOnClickListener {
            if (!viewModel.isAnimationPlaying) {
                updateObjectPositions()
                startAnimation()
                viewModel.updateCurrentState()
                updateObjectEndPositions()
            }
        }

        sceneView.viewTreeObserver.addOnGlobalLayoutListener(
            OnGlobalLayoutListener {
                updateObjectStartupPosition()
                updateObjectStartupColors()
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        sunAnimator?.cancel()
        sunColorAnimator?.cancel()
        moonAnimator?.cancel()
        moonColorAnimator?.cancel()
        skyColorAnimator?.cancel()
        groundColorAnimator?.cancel()
    }

    private fun updateObjectStartupColors() {
        when (viewModel.currentState) {
            1 -> {
                (sunView.background as GradientDrawable).setColor(sundayColor)
                skyView.setBackgroundColor(skyDayColor)
            }
            2 -> {
                (sunView.background as GradientDrawable).setColor(sunsetColor)
                skyView.setBackgroundColor(skySunsetColor)
            }

            3 -> {
                (moonView.background as GradientDrawable).setColor(moonColorBright)
                groundView.setBackgroundColor(groundColorNight)
                skyView.setBackgroundColor(skyNightColor)
            }

            4 -> {
                (sunView.background as GradientDrawable).setColor(sunriseColor)
                groundView.setBackgroundColor(groundColor)
                skyView.setBackgroundColor(skySunriseColor)
            }

            else -> {}
        }
    }

    private fun updateObjectStartupPosition() {
        updateObjectPositions()
        when (viewModel.currentState) {
            1, 2, 3, 4 -> {
                sunView.y = viewModel.currentSunPosition
                moonView.y = viewModel.currentMoonPosition
            }

            else -> {}
        }
    }


    private fun startAnimation() {
        viewModel.isAnimationPlaying = true
        when (viewModel.currentState) {
            1 -> {
                startDayToSunsetAnimation()
            }

            2 -> {
                startSunsetToNightAnimation()
            }

            3 -> {
                startNightToSunriseAnimation()
            }

            4 -> {
                startSunriseToDayAnimation()
            }

            else -> {}
        }
    }

    private fun updateObjectEndPositions() {
        viewModel.currentSunPosition = viewModel.currentSunEndPosition
        viewModel.currentMoonPosition = viewModel.currentMoonEndPosition
    }

    private fun updateObjectPositions() {
        when (viewModel.currentState) {
            1, 2, 3 -> {
                viewModel.currentSunPosition = getSunPosition(viewModel.currentState)
                viewModel.currentSunEndPosition = getSunPosition(viewModel.currentState + 1)
                viewModel.currentMoonPosition = getMoonPosition(viewModel.currentState)
                viewModel.currentMoonEndPosition = getMoonPosition(viewModel.currentState + 1)
            }
            4 -> {
                viewModel.currentSunPosition = getSunPosition(viewModel.currentState)
                viewModel.currentSunEndPosition = getSunPosition(1)
                viewModel.currentMoonPosition = getMoonPosition(viewModel.currentState)
                viewModel.currentMoonEndPosition = getMoonPosition(1)
            }

            else -> {}
        }
    }

    private fun getSunPosition(state: Int): Float {
        return when (state) {
            1 -> {
                skyView.height.toFloat() / 2 - sunView.height.toFloat() / 2
            }

            2, 4 -> {
                sceneView.height.toFloat() - groundView.height.toFloat() - sunView.height / 2
            }

            3 -> {
                sceneView.height.toFloat() - groundView.height.toFloat()
            }

            else -> {
                0.0F
            }
        }
    }

    private fun getMoonPosition(state: Int): Float {
        return when (state) {
            1, 2, 4 -> {
                sceneView.height.toFloat() - moonView.height.toFloat()
            }

            3 -> {
                skyView.height.toFloat() / 3 - moonView.height.toFloat() / 2
            }

            else -> {
                0.0F
            }
        }
    }

    private fun startDayToSunsetAnimation() {
        sunAnimator = getObjectMovementAnimator(
            sunView,
            viewModel.currentSunPosition,
            viewModel.currentSunEndPosition
        )
        skyColorAnimator = getObjectColorAnimator(skyView, skyDayColor, skySunsetColor)
        sunColorAnimator = getValueColorAnimator(sunView, sundayColor, sunsetColor)


        sunAnimator.start()
        skyColorAnimator.start()
        sunColorAnimator.start()
        lifecycleScope.launch {
            delay(ANIM_DURATION)
            viewModel.isAnimationPlaying = false
        }
    }

    private fun startSunsetToNightAnimation() {
        sunAnimator =
            getObjectMovementAnimator(
                sunView,
                viewModel.currentSunPosition,
                viewModel.currentSunEndPosition,
                ANIM_DURATION_SHORT
            )
        moonAnimator =
            getObjectMovementAnimator(
                moonView,
                viewModel.currentMoonPosition,
                viewModel.currentMoonEndPosition
            )
        skyColorAnimator = getObjectColorAnimator(skyView, skySunsetColor, skyNightColor)
        groundColorAnimator = getObjectColorAnimator(groundView, groundColor, groundColorNight)
        moonColorAnimator = getValueColorAnimator(moonView, moonColor, moonColorBright)


        sunAnimator.start()
        moonAnimator.start()
        skyColorAnimator.start()
        groundColorAnimator.start()
        moonColorAnimator.start()

        lifecycleScope.launch {
            delay(ANIM_DURATION)
            viewModel.isAnimationPlaying = false
        }
    }

    private fun startNightToSunriseAnimation() {
        sunAnimator =
            getObjectMovementAnimator(
                sunView,
                viewModel.currentSunPosition,
                viewModel.currentSunEndPosition,
                ANIM_DURATION_SHORT
            )
        moonAnimator = getObjectMovementAnimator(
            moonView,
            viewModel.currentMoonPosition,
            viewModel.currentMoonEndPosition,
            ANIM_DURATION_SHORT
        )
        skyColorAnimator =
            getObjectColorAnimator(skyView, skyNightColor, skySunriseColor, ANIM_DURATION_SHORT)
        sunColorAnimator =
            getValueColorAnimator(sunView, sunsetColor, sunriseColor, ANIM_DURATION_SHORT)
        groundColorAnimator =
            getObjectColorAnimator(groundView, groundColorNight, groundColor, ANIM_DURATION_SHORT)
        moonColorAnimator =
            getValueColorAnimator(moonView, moonColorBright, moonColor, ANIM_DURATION_SHORT)

        sunAnimator.start()
        moonAnimator.start()
        skyColorAnimator.start()
        sunColorAnimator.start()
        groundColorAnimator.start()
        moonColorAnimator.start()
        lifecycleScope.launch {
            delay(ANIM_DURATION_SHORT)
            viewModel.isAnimationPlaying = false
        }
    }

    private fun startSunriseToDayAnimation() {
        sunAnimator =
            getObjectMovementAnimator(
                sunView,
                viewModel.currentSunPosition,
                viewModel.currentSunEndPosition,
                ANIM_DURATION_SHORT
            )
        skyColorAnimator =
            getObjectColorAnimator(skyView, skySunriseColor, skyDayColor, ANIM_DURATION_SHORT)
        sunColorAnimator =
            getValueColorAnimator(sunView, sunriseColor, sundayColor, ANIM_DURATION_SHORT)

        sunAnimator.start()
        skyColorAnimator.start()
        sunColorAnimator.start()
        lifecycleScope.launch {
            delay(ANIM_DURATION_SHORT)
            viewModel.isAnimationPlaying = false
        }
    }

    fun getObjectMovementAnimator(
        view: View,
        startPos: Float,
        endPos: Float,
        animDuration: Long = ANIM_DURATION
    ): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "y", startPos, endPos).apply {
            duration = animDuration
            interpolator = AccelerateInterpolator()
        }
    }

    private fun getObjectColorAnimator(
        view: View,
        startColor: Int,
        endColor: Int,
        animDuration: Long = ANIM_DURATION
    ): ObjectAnimator {
        return ObjectAnimator
            .ofInt(view, "backgroundColor", startColor, endColor).apply {
                duration = animDuration
                setEvaluator(ArgbEvaluator())
            }
    }

    private fun getValueColorAnimator(
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

    companion object {
        private const val ANIM_DURATION = 2000L
        private const val ANIM_DURATION_SHORT = 1000L
    }
}