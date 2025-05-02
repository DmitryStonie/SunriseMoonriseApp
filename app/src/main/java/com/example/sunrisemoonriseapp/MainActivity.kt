package com.example.sunrisemoonriseapp

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrisemoonriseapp.animation.DayAnimation
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.AnimationState
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.AnimationState.*
import com.example.sunrisemoonriseapp.animation.DayAnimation.Companion.nextState
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.getAnimProgress
import com.example.sunrisemoonriseapp.recyclerview.DiffUtilCallback
import com.example.sunrisemoonriseapp.recyclerview.EventAdapter
import com.example.sunrisemoonriseapp.recyclerview.Item
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var sunView: ImageView
    private lateinit var moonView: View
    private lateinit var skyView: View
    private lateinit var cloudView: View
    private lateinit var groundView: View
    private lateinit var sceneView: View
    private lateinit var eventsRecyclerView: RecyclerView

    private val viewModel by viewModels<MainScreenViewModel>()

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
        Log.d(LOG_TAG, "MainActivity successfully created $this")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        sunView = findViewById(R.id.sun)
        moonView = findViewById(R.id.moon)
        cloudView = findViewById(R.id.cloud)
        skyView = findViewById(R.id.sky)
        groundView = findViewById(R.id.ground)
        sceneView = findViewById(R.id.scene)
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView)

        sceneView.setOnClickListener {
            if (!viewModel.isAnimationPlaying) {
                viewModel.startAnimators()
            }
        }

        val adapter = EventAdapter(arrayListOf())
        adapter.onClickListener = EventAdapter.OnClickListener {
            Log.d("CLICK", "Item clicked {$it}")
        }
        eventsRecyclerView.layoutManager = LinearLayoutManager(this)
        eventsRecyclerView.adapter = adapter


        val latitude = "55.0415"
        val longitude = "82.9346"
        viewModel.getDayInfo(latitude, longitude).observe(this, { day ->
            val events = ArrayList<Item>()
            if (day == null) {
                Log.d("DATA", "day is null")
            } else {
                Log.d("TIME", day.toString())
                events.add(
                    Item(
                        "Длина дня",
                        (day.sunset.start - day.sunrise.start).getTimeValue()
                    )
                )
                events.add(Item("Рассвет", day.sunrise.start.getTimeValue()))
                events.add(Item("Золотой час", day.goldenHour.start.getTimeValue()))
                events.add(Item("Закат", day.sunset.start.getTimeValue()))
            }
            val diffUtilCallback = DiffUtilCallback(adapter.events, events)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            adapter.events = events
            diffResult.dispatchUpdatesTo(adapter)
        })


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
                cloudView.x = 0F
                cloudView.isVisible = true
                val cloudAnimator = getCloudAnimator()
                cloudAnimator.repeatCount = ValueAnimator.INFINITE
                cloudAnimator.start()
            }
        )
        val slider = findViewById<Slider>(R.id.slider)
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being stopped
            }
        })

        slider.addOnChangeListener { slider, value, fromUser ->
            getNewSun(viewModel.day, value.toInt(), sunView.y)!!.start()
            getNewMoon(viewModel.day, value.toInt(), moonView.y)!!.start()
            Log.d("TIME", "Got time ${value.toInt()} ")
        }


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
//        viewModel.sunAnimator?.changeAnimations(
//            getSunAnimator(
//                viewModel.state,
//                viewModel.sunAnimator?.animationCompletion ?: 0.0F
//            ), getSunAnimations()
//        )
//        viewModel.moonAnimator?.changeAnimations(
//            getMoonAnimator(
//                viewModel.state,
//                viewModel.moonAnimator?.animationCompletion ?: 0.0F
//            ), getMoonAnimations()
//        )
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

    private fun updateSunColor(state: AnimationState) {
        when (state) {
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

//    private fun updateSunPosition(state: AnimationState) {
//        when (state) {
//            DAY -> {
//                sunView.y = getSunPosition(DAY)
//
//            }
//
//            SUNSET -> {
//                sunView.y = getSunPosition(SUNSET)
//
//            }
//
//            NIGHT -> {
//                sunView.y = getSunPosition(NIGHT)
//
//            }
//
//            SUNRISE -> {
//                sunView.y = getSunPosition(SUNRISE)
//
//            }
//        }
//    }

//    private fun updateMoonPosition(state: AnimationState) {
//        when (state) {
//            DAY -> {
//                moonView.y = getMoonPosition(DAY)
//
//            }
//
//            SUNSET -> {
//                moonView.y = getMoonPosition(SUNSET)
//
//            }
//
//            NIGHT -> {
//                moonView.y = getMoonPosition(NIGHT)
//
//            }
//
//            SUNRISE -> {
//                moonView.y = getMoonPosition(SUNRISE)
//
//            }
//        }
//    }
//
    private fun updateMoonColor(state: AnimationState) {
        when (state) {
            NIGHT -> {
                (moonView.background as GradientDrawable).setColor(moonColorBright)

            }

            else -> {}
        }
    }

    private fun updateSkyColor(state: AnimationState) {
        when (state) {
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

    private fun updateGroundColor(state: AnimationState) {
        when (state) {
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
        if (viewModel.sunColorAnimator?.isCompleted == true) {
            updateSunColor(nextState(viewModel.state))
        }
        if (viewModel.sunAnimator?.isCompleted == true) {
//            updateSunPosition(nextState(viewModel.state))
        }
        if (viewModel.moonColorAnimator?.isCompleted == true) {
            updateMoonColor(nextState(viewModel.state))
        }
//        if (viewModel.moonAnimator?.isCompleted == true) {
//            updateMoonPosition(nextState(viewModel.state))
//        }
        if (viewModel.skyColorAnimator?.isCompleted == true) {
            updateSkyColor(nextState(viewModel.state))
        }
        if (viewModel.groundColorAnimator?.isCompleted == true) {
            updateGroundColor(nextState(viewModel.state))
        }
    }

    private fun getSunPosition(day: Day, time: Int): Float {
        return if(time >= day.dawn.start && time < day.dawn.end){
            return sceneView.height.toFloat() - getAnimProgress(day.dawn.start, day.dawn.end, time) * groundView.height.toFloat()
        } else if(time >= day.sunrise.start && time < day.sunrise.end){
            return sceneView.height.toFloat() - groundView.height.toFloat() - getAnimProgress(day.sunrise.start, day.sunrise.end, time) * sunView.height.toFloat()
        } else if(time >= day.day.start && time < day.solarNoon.start){
            return skyView.height.toFloat() - sunView.height.toFloat() - getAnimProgress(day.day.start, day.solarNoon.start, time ) * 0.75F * (skyView.height.toFloat() - sunView.height.toFloat())
        } else if(time >= day.solarNoon.end && time < day.goldenHour.end){
            return 0.25F * (skyView.height.toFloat() - sunView.height.toFloat()) + getAnimProgress(day.solarNoon.end, day.goldenHour.end, time ) * 0.75F *(skyView.height.toFloat() - sunView.height.toFloat())
        } else if(time >= day.sunset.start && time < day.sunset.end){
            return (skyView.height.toFloat() - sunView.height.toFloat()) + getAnimProgress(day.sunset.start, day.sunset.end, time) * sunView.height.toFloat()
        } else if(time >= day.dusk.start && time < day.dusk.end){
            return skyView.height.toFloat() + getAnimProgress(day.dusk.start, day.dusk.end, time) * groundView.height.toFloat()
        } else{
            return sceneView.height.toFloat()
        }
    }

    private fun getMoonPosition(day: Day, time: Int): Float {
        return if(time >= day.dusk.start && time <= day.nightEnd.end){
            return skyView.height.toFloat() - getAnimProgress(day.dusk.start, day.nightEnd.end, time) *  0.8F * skyView.height.toFloat()
        } else if(time >= day.nightStart.start && time <= day.dawn.end){
            return 0.2F * skyView.height.toFloat() + getAnimProgress(day.nightStart.start, day.dawn.end, time) *  0.8F * skyView.height.toFloat()
        } else{
            return return sceneView.height.toFloat()
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

//        return ObjectAnimator.ofFloat(view, "y", startPos, endPos).apply {
//            duration = animDuration
//            interpolator = AccelerateInterpolator()
//        }
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
//            result[state] = getSunAnimator(state, animationFraction)
        }
        return result
    }

    private fun getMoonAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
        val result = HashMap<AnimationState, ValueAnimator?>()
        for (state in AnimationState.entries) {
//            result[state] = getMoonAnimator(state, animationFraction)
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

//    private fun getSunAnimator(
//        animationsState: AnimationState,
//        animationFraction: Float = 0.0F
//    ): ValueAnimator? {
//        return when (animationsState) {
//            DAY -> {
//                getMovementAnimator(
//                    sunView,
//                    getSunPosition(DAY) + animationFraction * (getSunPosition(
//                        SUNSET
//                    ) - getSunPosition(DAY)),
//                    getSunPosition(SUNSET),
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNSET -> {
//                getMovementAnimator(
//                    sunView,
//                    getSunPosition(SUNSET) + animationFraction * (getSunPosition(
//                        NIGHT
//                    ) - getSunPosition(SUNSET)),
//                    getSunPosition(NIGHT),
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            NIGHT -> {
//                getMovementAnimator(
//                    sunView,
//                    getSunPosition(NIGHT) + animationFraction * (getSunPosition(
//                        SUNRISE
//                    ) - getSunPosition(NIGHT)),
//                    getSunPosition(SUNRISE),
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNRISE -> {
//                getMovementAnimator(
//                    sunView,
//                    getSunPosition(SUNRISE) + animationFraction * (getSunPosition(
//                        DAY
//                    ) - getSunPosition(SUNRISE)),
//                    getSunPosition(DAY),
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//        }
//    }

//    private fun getMoonAnimator(
//        animationsState: AnimationState,
//        animationFraction: Float = 0.0F
//    ): ValueAnimator? {
//        return when (animationsState) {
//            DAY -> {
//                null
//            }
//
//            SUNSET -> {
//                getMovementAnimator(
//                    moonView,
//                    getMoonPosition(SUNSET) + animationFraction * (getMoonPosition(
//                        NIGHT
//                    ) - getMoonPosition(SUNSET)),
//                    getMoonPosition(NIGHT),
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            NIGHT -> {
//                getMovementAnimator(
//                    moonView,
//                    getMoonPosition(NIGHT) + animationFraction * (getMoonPosition(
//                        SUNRISE
//                    ) - getMoonPosition(NIGHT)),
//                    getMoonPosition(SUNRISE),
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNRISE -> {
//                null
//            }
//        }
//    }

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

    private fun getCloudAnimator(
    ): ValueAnimator {
        return ValueAnimator.ofFloat(
            -cloudView.width.toFloat(), sceneView.width.toFloat()
        )
            .apply {
                interpolator = LinearInterpolator()
                duration = 2000
                addUpdateListener { animator ->
                    cloudView.x = animator.animatedValue as Float
                }
            }

    }

    private fun getNewMoon(
        day: Day,
        time: Int,
        currentPos: Float
    ): ValueAnimator? {
        return getMovementAnimator(
            moonView,
            currentPos,
            getMoonPosition(day, time),
            animDuration = ANIM_DURATION
        )
    }

    private fun getNewSun(
        day: Day,
        time: Int,
        currentPos: Float
    ): ValueAnimator? {
        return getMovementAnimator(
                sunView,
                currentPos,
                getSunPosition(day, time),
                animDuration = ANIM_DURATION
            )
    }

    companion object {
        const val ANIM_DURATION = 2000L
        private const val LOG_TAG = "MainActivityTag"
    }
}

fun String.getSecondsValue(): Int {
    val digits = this.split(":").map { it.toInt() }
    return digits[0] * 3600 + digits[1] * 60 + digits[2]
}

fun Int.getTimeValue(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
}

fun getAnimProgress(start: Int, end: Int, current: Int): Float{
    return (1F * current - start) / (1F * end - start)
}
