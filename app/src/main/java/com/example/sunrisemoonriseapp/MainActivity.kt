package com.example.sunrisemoonriseapp

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.recyclerview.DiffUtilCallback
import com.example.sunrisemoonriseapp.recyclerview.EventAdapter
import com.example.sunrisemoonriseapp.recyclerview.Item
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var sunView: View
    private lateinit var moonView: View
    private lateinit var skyView: View
    private lateinit var cloudView: View
    private lateinit var groundView: View
    private lateinit var sceneView: View
    private lateinit var eventsRecyclerView: RecyclerView

    private val viewModel by viewModels<MainScreenViewModel>()

    private val skyDawn1Color: Int by lazy {
        getColor(R.color.sky_dawn1)
    }
    private val skyDawn2Color: Int by lazy {
        getColor(R.color.sky_dawn2)
    }
    private val skyDawn3Color: Int by lazy {
        getColor(R.color.sky_dawn3)
    }
    private val skyDawn4Color: Int by lazy {
        getColor(R.color.sky_dawn4)
    }
    private val skyDawn5Color: Int by lazy {
        getColor(R.color.sky_dawn5)
    }
    private val skySunrise1Color: Int by lazy {
        getColor(R.color.sky_sunrise1)
    }
    private val skySunrise2Color: Int by lazy {
        getColor(R.color.sky_sunrise2)
    }
    private val skySunrise3Color: Int by lazy {
        getColor(R.color.sky_sunrise3)
    }
    private val skySunrise4Color: Int by lazy {
        getColor(R.color.sky_sunrise4)
    }
    private val skySunrise5Color: Int by lazy {
        getColor(R.color.sky_sunrise5)
    }
    private val skyDay1Color: Int by lazy {
        getColor(R.color.sky_day1)
    }
    private val skyDay2Color: Int by lazy {
        getColor(R.color.sky_day2)
    }
    private val skyDay3Color: Int by lazy {
        getColor(R.color.sky_day3)
    }
    private val skyDay4Color: Int by lazy {
        getColor(R.color.sky_day4)
    }
    private val skyGoldenHour1Color: Int by lazy {
        getColor(R.color.sky_goldenhour1)
    }
    private val skyGoldenHour2Color: Int by lazy {
        getColor(R.color.sky_goldenhour2)
    }
    private val skySunset1Color: Int by lazy {
        getColor(R.color.sky_sunset1)
    }
    private val skySunset2Color: Int by lazy {
        getColor(R.color.sky_sunset2)
    }
    private val skySunset3Color: Int by lazy {
        getColor(R.color.sky_sunset3)
    }
    private val skyDuskColor1: Int by lazy {
        getColor(R.color.sky_dusk1)
    }
    private val skyDuskColor2: Int by lazy {
        getColor(R.color.sky_dusk2)
    }
    private val skyDuskColor3: Int by lazy {
        getColor(R.color.sky_dusk3)
    }
    private val skyDuskColor4: Int by lazy {
        getColor(R.color.sky_dusk4)
    }
    private val skyDuskColor5: Int by lazy {
        getColor(R.color.sky_dusk5)
    }
    private val skyNightColor: Int by lazy {
        getColor(R.color.sky_night)
    }
    private val sunSunriseColor1: Int by lazy {
        getColor(R.color.sun_sunrise1)
    }
    private val sunSunriseColor2: Int by lazy {
        getColor(R.color.sun_sunrise2)
    }
    private val sunSunriseColor3: Int by lazy {
        getColor(R.color.sun_sunrise3)
    }
    private val sunSunriseColor4: Int by lazy {
        getColor(R.color.sun_sunrise4)
    }
    private val sunSunriseColor5: Int by lazy {
        getColor(R.color.sun_sunrise5)
    }
    private val sunDayColor: Int by lazy {
        getColor(R.color.sun_sunday)
    }
    private val sunGoldenHour1Color: Int by lazy {
        getColor(R.color.sun_goldenhour1)
    }
    private val sunGoldenHour2Color: Int by lazy {
        getColor(R.color.sun_goldenhour2)
    }
    private val sunSunsetColor1: Int by lazy {
        getColor(R.color.sky_sunset1)
    }
    private val sunSunsetColor2: Int by lazy {
        getColor(R.color.sky_sunset1)
    }
    private val sunSunsetColor3: Int by lazy {
        getColor(R.color.sky_sunset1)
    }
    private val moonColor: Int by lazy {
        getColor(R.color.moon)
    }
    private val moonColorBright: Int by lazy {
        getColor(R.color.moonBright)
    }
    private val groundColor: Int by lazy {
        getColor(R.color.ground_day)
    }
    private val groundColorNight: Int by lazy {
        getColor(R.color.ground_night)
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
        viewModel.getDayInfo(latitude, longitude).observe(this) { day ->
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
        }


        sceneView.viewTreeObserver.addOnGlobalLayoutListener(
            OnGlobalLayoutListener {
                if (!viewModel.isInitialized) {
                    Log.d(LOG_TAG, "MainActivity initialized ViewModel")
                } else if (!viewModel.isUpdated) {
                    Log.d(LOG_TAG, "MainActivity updated ViewModel")
                }
                if (viewModel.isCancelled) {
                    viewModel.continueAnimators()
                    Log.d(LOG_TAG, "MainActivity started animations")
                } else {
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
            getNewSunColor(
                viewModel.day,
                value.toInt(),
                (sunView.background as GradientDrawable).color!!.defaultColor
            )!!.start()
            getNewMoonColor(
                viewModel.day,
                value.toInt(),
                (moonView.background as GradientDrawable).color!!.defaultColor
            )!!.start()
            getNewSkyColor(
                viewModel.day,
                value.toInt(),
                (skyView.background as ColorDrawable).color
            )!!.start()
            getNewGroundColor(
                viewModel.day,
                value.toInt(),
                (groundView.background as ColorDrawable).color
            )!!.start()
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

    private fun getSunPosition(day: Day, time: Int): Float {
        return if (time >= day.dawn.start && time < day.dawn.end) {
            return sceneView.height.toFloat() - getAnimProgress(
                day.dawn.start,
                day.dawn.end,
                time
            ) * groundView.height.toFloat()
        } else if (time >= day.sunrise.start && time < day.sunrise.end) {
            return sceneView.height.toFloat() - groundView.height.toFloat() - getAnimProgress(
                day.sunrise.start,
                day.sunrise.end,
                time
            ) * sunView.height.toFloat()
        } else if (time >= day.day.start && time < day.solarNoon.start) {
            return skyView.height.toFloat() - sunView.height.toFloat() - getAnimProgress(
                day.day.start,
                day.solarNoon.start,
                time
            ) * 0.75F * (skyView.height.toFloat() - sunView.height.toFloat())
        } else if (time >= day.solarNoon.end && time < day.goldenHour.end) {
            return 0.25F * (skyView.height.toFloat() - sunView.height.toFloat()) + getAnimProgress(
                day.solarNoon.end,
                day.goldenHour.end,
                time
            ) * 0.75F * (skyView.height.toFloat() - sunView.height.toFloat())
        } else if (time >= day.sunset.start && time < day.sunset.end) {
            return (skyView.height.toFloat() - sunView.height.toFloat()) + getAnimProgress(
                day.sunset.start,
                day.sunset.end,
                time
            ) * sunView.height.toFloat()
        } else if (time >= day.dusk.start && time < day.dusk.end) {
            return skyView.height.toFloat() + getAnimProgress(
                day.dusk.start,
                day.dusk.end,
                time
            ) * groundView.height.toFloat()
        } else {
            return sceneView.height.toFloat()
        }
    }

    private fun getMoonPosition(day: Day, time: Int): Float {
        return if (time >= day.dusk.start && time <= day.nightEnd.end) {
            return skyView.height.toFloat() - getAnimProgress(
                day.dusk.start,
                day.nightEnd.end,
                time
            ) * 0.8F * skyView.height.toFloat()
        } else if (time >= day.nightStart.start && time <= day.dawn.end) {
            return 0.2F * skyView.height.toFloat() + getAnimProgress(
                day.nightStart.start,
                day.dawn.end,
                time
            ) * 0.8F * skyView.height.toFloat()
        } else {
            return return sceneView.height.toFloat()
        }
    }

    fun getMovementAnimator(
        view: View,
        startPos: Float,
        endPos: Float
    ): ValueAnimator {
        return ValueAnimator.ofFloat(
            startPos, endPos
        )
            .apply {
                interpolator = AccelerateInterpolator()
                duration = ANIM_DURATION
                addUpdateListener { animator ->
                    view.y = animator.animatedValue as Float
                }
            }
    }

    private fun getViewColorAnimator(
        view: View,
        startColor: Int,
        endColor: Int): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(),
            startColor,
            endColor
        ).apply {
            duration = ANIM_DURATION
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

//    private fun getSunAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
//        val result = HashMap<AnimationState, ValueAnimator?>()
//        for (state in AnimationState.entries) {
////            result[state] = getSunAnimator(state, animationFraction)
//        }
//        return result
//    }
//
//    private fun getMoonAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
//        val result = HashMap<AnimationState, ValueAnimator?>()
//        for (state in AnimationState.entries) {
////            result[state] = getMoonAnimator(state, animationFraction)
//        }
//        return result
//    }
//
//    private fun getSunColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
//        val result = HashMap<AnimationState, ValueAnimator?>()
//        for (state in AnimationState.entries) {
////            result[state] = getSunColorAnimator(state, animationFraction)
//        }
//        return result
//    }
//
//    private fun getMoonColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
//        val result = HashMap<AnimationState, ValueAnimator?>()
//        for (state in AnimationState.entries) {
////            result[state] = getMoonColorAnimator(state, animationFraction)
//        }
//        return result
//    }
//
//    private fun getSkyColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
//        val result = HashMap<AnimationState, ValueAnimator?>()
//        for (state in AnimationState.entries) {
////            result[state] = getSkyColorAnimator(state, animationFraction)
//        }
//        return result
//    }
//
//    private fun getGroundColorAnimations(animationFraction: Float = 0.0F): HashMap<AnimationState, ValueAnimator?> {
//        val result = HashMap<AnimationState, ValueAnimator?>()
//        for (state in AnimationState.entries) {
////            result[state] = getGroundColorAnimator(state, animationFraction)
//        }
//        return result
//    }

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

//    private fun getSunColorAnimator(
//        animationsState: AnimationState,
//        animationFraction: Float = 0.0F
//    ): ValueAnimator? {
//        return when (animationsState) {
//            DAY -> {
//                getDrawableColorAnimator(
//                    sunView,
//                    sundayColor,
//                    sunsetColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNSET -> {
//                null
//            }
//
//            NIGHT -> {
//                getDrawableColorAnimator(
//                    sunView,
//                    sunsetColor,
//                    sunriseColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNRISE -> {
//                getDrawableColorAnimator(
//                    sunView,
//                    sunriseColor,
//                    sundayColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//        }
//    }

//    private fun getMoonColorAnimator(
//        animationsState: AnimationState,
//        animationFraction: Float = 0.0F
//    ): ValueAnimator? {
//        return when (animationsState) {
//            DAY -> {
//                null
//            }
//
//            SUNSET -> {
//                getDrawableColorAnimator(
//                    moonView,
//                    moonColor,
//                    moonColorBright,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            NIGHT -> {
//                getDrawableColorAnimator(
//                    moonView,
//                    moonColorBright,
//                    moonColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNRISE -> {
//                null
//            }
//        }
//    }

//    private fun getSkyColorAnimator(
//        animationsState: AnimationState,
//        animationFraction: Float = 0.0F
//    ): ValueAnimator? {
//        return when (animationsState) {
//            DAY -> {
//                getViewColorAnimator(
//                    skyView,
//                    skyDayColor,
//                    skySunsetColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNSET -> {
//                getViewColorAnimator(
//                    skyView,
//                    skySunsetColor,
//                    skyNightColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            NIGHT -> {
//                getViewColorAnimator(
//                    skyView,
//                    skyNightColor,
//                    skySunriseColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNRISE -> {
//                getViewColorAnimator(
//                    skyView,
//                    skySunriseColor,
//                    skyDayColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//        }
//    }
//
//    private fun getGroundColorAnimator(
//        animationsState: AnimationState,
//        animationFraction: Float = 0.0F
//    ): ValueAnimator? {
//        return when (animationsState) {
//            DAY -> {
//                null
//            }
//
//            SUNSET -> {
//                getViewColorAnimator(
//                    groundView,
//                    groundColor,
//                    groundColorNight,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            NIGHT -> {
//                getViewColorAnimator(
//                    groundView,
//                    groundColorNight,
//                    groundColor,
//                    animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
//                )
//            }
//
//            SUNRISE -> {
//                null
//            }
//        }
//    }

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

    private fun getSunColor(day: Day, time: Int): Int {
        var s = 0
        var e = 0
        var l = 0
        return if (time >= day.sunrise.start && time < day.sunrise.end) {
            s = day.sunrise.start
            e = day.sunrise.end
            l = e - s
            if (time >= s && time < s + l * 0.2) {
                sunSunriseColor1
            } else if (time >= s + l * 0.2 && time < s + l * 0.4) {
                sunSunriseColor2
            } else if (time >= s + l * 0.4 && time < s + l * 0.6) {
                sunSunriseColor3
            } else if (time >= s + l * 0.6 && time < s + l * 0.8) {
                sunSunriseColor4
            } else {
                sunSunriseColor5
            }
        } else if (time >= day.day.start && time < day.day.end) {
            sunDayColor
        } else if (time >= day.goldenHour.start && time < day.goldenHour.end) {
            s = day.goldenHour.start
            e = day.goldenHour.end
            l = e - s
            if (time >= s && time < s + l * 0.5) {
                sunGoldenHour1Color
            } else{
                sunGoldenHour2Color
            }
        } else if (time >= day.sunset.start && time < day.sunset.end) {
            s = day.goldenHour.start
            e = day.goldenHour.end
            l = e - s
            if (time >= s && time < s + l * 0.33) {
                sunSunsetColor1
            } else if(time >= s + l * 0.33 && time < s + l * 0.66) {
                sunSunsetColor2
            }else{
                sunSunsetColor3
            }
        } else {
            sunDayColor
        }
    }

    private fun getMoonColor(day: Day, time: Int): Int {
        return if ((time >= day.nightStart.start && time < day.nightStart.end)
            || (time >= day.nightEnd.start && time < day.nightEnd.end)
        ) {
            moonColorBright
        } else {
            moonColor
        }
    }

    private fun getGroundColor(day: Day, time: Int): Int {
        return if ((time >= day.nightStart.start && time < day.nightStart.end)
            || (time >= day.nightEnd.start && time < day.nightEnd.end)
        ) {
            groundColorNight
        } else {
            groundColor
        }
    }

    private fun getSkyColor(day: Day, time: Int): Int {
        var s = 0
        var e = 0
        var l = 0
        return if(time >= day.dawn.start && time < day.dawn.end){
            s = day.dawn.start
            e = day.dawn.end
            l = e - s
            if(time >= s && time < s + l * 0.2){
                skyDawn1Color
            } else if(time >= s + l * 0.2 && time < s + l * 0.4){
                skyDawn2Color
            } else if(time >= s + l * 0.4 && time < s + l * 0.6){
                skyDawn3Color
            } else if(time >= s + l * 0.6 && time < s + l * 0.8){
                skyDawn4Color
            } else{
                skyDawn5Color
            }
        } else if(time >= day.sunrise.start && time < day.sunrise.end){
            s = day.sunrise.start
            e = day.sunrise.end
            l = e - s
            if(time >= s && time < s + l * 0.2){
                skySunrise1Color
            } else if(time >= s + l * 0.2 && time < s + l * 0.4){
                skySunrise2Color
            } else if(time >= s + l * 0.4 && time < s + l * 0.6){
                skySunrise3Color
            } else if(time >= s + l * 0.6 && time < s + l * 0.8){
                skySunrise4Color
            } else{
                skySunrise5Color
            }
        } else if(time >= day.day.start && time < day.solarNoon.start){
            s = day.day.start
            e = day.solarNoon.start
            l = e - s
            if(time >= s && time < s + l * 0.25){
                skyDay1Color
            } else if(time >= s + l * 0.25 && time < s + l * 0.5){
                skyDay2Color
            } else if(time >= s + l * 0.5 && time < s + l * 0.75){
                skyDay3Color
            }else{
                skyDay4Color
            }
        } else if(time >= day.solarNoon.end && time < day.day.end){
            s = day.solarNoon.end
            e = day.day.end
            l = e - s
            if(time >= s && time < s + l * 0.25){
                skyDay4Color
            } else if(time >= s + l * 0.25 && time < s + l * 0.5){
                skyDay3Color
            } else if(time >= s + l * 0.5 && time < s + l * 0.75){
                skyDay2Color
            }else{
                skyDay1Color
            }
        } else if(time >= day.goldenHour.start && time < day.goldenHour.end){
            s = day.goldenHour.start
            e = day.goldenHour.end
            l = e - s
            if(time >= s && time < s + l * 0.5){
                skyGoldenHour1Color
            } else {
                skyGoldenHour2Color
            }
        } else if(time >= day.sunset.start && time < day.sunset.end){
            s = day.sunset.start
            e = day.sunset.end
            l = e - s
            if(time >= s && time < s + l * 0.33){
                skySunset1Color
            } else if(time >= s + l*0.33 && time < s + l * 0.66){
                skySunset2Color
            } else{
                skySunset3Color
            }
        } else if(time >= day.dusk.start && time < day.dusk.end){
            s = day.dusk.start
            e = day.dusk.end
            l = e - s
            if(time >= s && time < s + l * 0.2){
                skyDuskColor1
            } else if(time >= s + l * 0.2 && time < s + l * 0.4){
                skyDuskColor2
            } else if(time >= s + l * 0.4 && time < s + l * 0.6){
                skyDuskColor3
            } else if(time >= s + l * 0.6 && time < s + l * 0.8){
                skyDuskColor4
            } else{
                skyDuskColor5
            }
        } else{
            skyNightColor
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
            getMoonPosition(day, time))
    }

    private fun getNewSun(
        day: Day,
        time: Int,
        currentPos: Float
    ): ValueAnimator? {
        return getMovementAnimator(
            sunView,
            currentPos,
            getSunPosition(day, time))
    }

    private fun getNewSunColor(
        day: Day,
        time: Int,
        currentColor: Int
    ): ValueAnimator? {
        return getDrawableColorAnimator(
            sunView,
            currentColor,
            getSunColor(day, time))
    }

    private fun getNewMoonColor(
        day: Day,
        time: Int,
        currentColor: Int
    ): ValueAnimator? {
        return getDrawableColorAnimator(
            moonView,
            currentColor,
            getMoonColor(day, time)
        )
    }

    private fun getNewSkyColor(
        day: Day,
        time: Int,
        currentColor: Int
    ): ValueAnimator? {
        return getViewColorAnimator(
            skyView,
            currentColor,
            getSkyColor(day, time))
    }

    private fun getNewGroundColor(
        day: Day,
        time: Int,
        currentColor: Int
    ): ValueAnimator? {
        return getViewColorAnimator(
            groundView,
            currentColor,
            getGroundColor(day, time))
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

    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
}

fun getAnimProgress(start: Int, end: Int, current: Int): Float {
    return (1F * current - start) / (1F * end - start)
}
