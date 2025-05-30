package com.example.sunrisemoonriseapp.recyclerview.viewholders

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.MenuRes
import com.example.sunrisemoonriseapp.R
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.entities.Moon
import com.example.sunrisemoonriseapp.getAnimProgress
import com.example.sunrisemoonriseapp.recyclerview.items.SkyItem
import com.example.sunrisemoonriseapp.ui.CustomPainter
import com.example.sunrisemoonriseapp.ui.MoonPainter
import java.util.Calendar
import kotlin.math.abs
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round

class SkyViewHolder(val view: View) : BaseViewHolder(view) {
    private val skyDawn1Color: Int by lazy {
        view.context.getColor(R.color.sky_dawn1)
    }
    private val skyDawn2Color: Int by lazy {
        view.context.getColor(R.color.sky_dawn2)
    }
    private val skyDawn3Color: Int by lazy {
        view.context.getColor(R.color.sky_dawn3)
    }
    private val skyDawn4Color: Int by lazy {
        view.context.getColor(R.color.sky_dawn4)
    }
    private val skyDawn5Color: Int by lazy {
        view.context.getColor(R.color.sky_dawn5)
    }
    private val skySunrise1Color: Int by lazy {
        view.context.getColor(R.color.sky_sunrise1)
    }
    private val skySunrise2Color: Int by lazy {
        view.context.getColor(R.color.sky_sunrise2)
    }
    private val skySunrise3Color: Int by lazy {
        view.context.getColor(R.color.sky_sunrise3)
    }
    private val skySunrise4Color: Int by lazy {
        view.context.getColor(R.color.sky_sunrise4)
    }
    private val skySunrise5Color: Int by lazy {
        view.context.getColor(R.color.sky_sunrise5)
    }
    private val skyDay1Color: Int by lazy {
        view.context.getColor(R.color.sky_day1)
    }
    private val skyDay2Color: Int by lazy {
        view.context.getColor(R.color.sky_day2)
    }
    private val skyDay3Color: Int by lazy {
        view.context.getColor(R.color.sky_day3)
    }
    private val skyDay4Color: Int by lazy {
        view.context.getColor(R.color.sky_day4)
    }
    private val skyGoldenHour1Color: Int by lazy {
        view.context.getColor(R.color.sky_goldenhour1)
    }
    private val skyGoldenHour2Color: Int by lazy {
        view.context.getColor(R.color.sky_goldenhour2)
    }
    private val skySunset1Color: Int by lazy {
        view.context.getColor(R.color.sky_sunset1)
    }
    private val skySunset2Color: Int by lazy {
        view.context.getColor(R.color.sky_sunset2)
    }
    private val skySunset3Color: Int by lazy {
        view.context.getColor(R.color.sky_sunset3)
    }
    private val skyDuskColor1: Int by lazy {
        view.context.getColor(R.color.sky_dusk1)
    }
    private val skyDuskColor2: Int by lazy {
        view.context.getColor(R.color.sky_dusk2)
    }
    private val skyDuskColor3: Int by lazy {
        view.context.getColor(R.color.sky_dusk3)
    }
    private val skyDuskColor4: Int by lazy {
        view.context.getColor(R.color.sky_dusk4)
    }
    private val skyDuskColor5: Int by lazy {
        view.context.getColor(R.color.sky_dusk5)
    }
    private val skyNightColor: Int by lazy {
        view.context.getColor(R.color.sky_night)
    }
    private val sunSunriseColor1: Int by lazy {
        view.context.getColor(R.color.sun_sunrise1)
    }
    private val sunSunriseColor2: Int by lazy {
        view.context.getColor(R.color.sun_sunrise2)
    }
    private val sunSunriseColor3: Int by lazy {
        view.context.getColor(R.color.sun_sunrise3)
    }
    private val sunSunriseColor4: Int by lazy {
        view.context.getColor(R.color.sun_sunrise4)
    }
    private val sunSunriseColor5: Int by lazy {
        view.context.getColor(R.color.sun_sunrise5)
    }
    private val sunDayColor: Int by lazy {
        view.context.getColor(R.color.sun_sunday)
    }
    private val sunGoldenHour1Color: Int by lazy {
        view.context.getColor(R.color.sun_goldenhour1)
    }
    private val sunGoldenHour2Color: Int by lazy {
        view.context.getColor(R.color.sun_goldenhour2)
    }
    private val sunSunsetColor1: Int by lazy {
        view.context.getColor(R.color.sun_sunset1)
    }
    private val sunSunsetColor2: Int by lazy {
        view.context.getColor(R.color.sun_sunset2)
    }
    private val sunSunsetColor3: Int by lazy {
        view.context.getColor(R.color.sun_sunset3)
    }
    private val moonColor: Int by lazy {
        view.context.getColor(R.color.moon)
    }
    private val groundColor: Int by lazy {
        view.context.getColor(R.color.ground_day)
    }
    private val groundColorNight: Int by lazy {
        view.context.getColor(R.color.ground_night)
    }
    private val moonColorBright: Int by lazy {
        view.context.getColor(R.color.moonBright)
    }
    private val moonColorDark: Int by lazy {
        view.context.getColor(R.color.moonDark)
    }

    private val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US)
    var animations = AnimatorSet()
    val sunView: View = view.findViewById(R.id.sun)
    lateinit var moonView: View
    val moonViewLayout: FrameLayout = view.findViewById<FrameLayout>(R.id.moon)
    val cloudView: View = view.findViewById(R.id.cloud)
    val skyView: View = view.findViewById(R.id.sky)
    val groundView: View = view.findViewById(R.id.ground)
    val sceneView: View = view.findViewById(R.id.scene)
    val clockView: TextView = view.findViewById<TextView>(R.id.clock)
    val resetButtonView: ImageView = view.findViewById<ImageView>(R.id.resetButton)
    val placeButtonView: ImageView = view.findViewById<ImageView>(R.id.placeButton)
    val aboutButtonView: ImageView = view.findViewById<ImageView>(R.id.aboutButton)
    val menuButtonView: ImageView = view.findViewById<ImageView>(R.id.menuButton)
    val placeNameView: TextView = view.findViewById<TextView>(R.id.placeName)
    val temperatureTextView: TextView = view.findViewById<TextView>(R.id.temperatureText)
    val weatherNameTextView: TextView = view.findViewById<TextView>(R.id.weatherName)


    fun bind(item: SkyItem) {
        // crap
        item.moonLiveData.observe(view.context as LifecycleOwner) {
            val width = abs(2 * it.illumination - 1F).toFloat()
            val painter = if (it.age < Moon.FULL_MOON_AGE) {
                if (it.illumination <= 0.5F) {
                    MoonPainter(moonColorBright, moonColorDark, leftWidth = width)
                } else {
                    MoonPainter(moonColorDark, moonColorBright, rightWidth = width)
                }
            } else {
                if (it.illumination > 0.5F) {
                    MoonPainter(moonColorDark, moonColorBright, leftWidth = width)
                } else {
                    MoonPainter(moonColorBright, moonColorDark, rightWidth = width)
                }
            }
            moonView = CustomPainter(view.context, 90, 90, painter)
            moonViewLayout.addView(moonView)
        }
        // fix this!!!!!
        item.timeLiveData.observe(view.context as LifecycleOwner) {
            val date = Date(it)
            val calendar = Calendar.getInstance()
            calendar.time = date
            clockView.text = formatter.format(date)
            updateAnimations(
                calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(
                    Calendar.SECOND
                ),
                item.animDurationLiveData.value ?: ANIM_DURATION, item.dayLiveData.value
            )
        }
        item.placeLiveData.observe(view.context as LifecycleOwner) { place ->
            if (place.name == "") {
                placeNameView.isVisible = false
            } else {
                placeNameView.isVisible = true
                placeNameView.text = place.name
            }
        }
        item.weatherLiveData.observe(view.context as LifecycleOwner) { weather ->
            temperatureTextView.text = "${round(weather.temperature).toInt()}℃"
            weatherNameTextView.text = weather.description.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
        resetButtonView.setOnClickListener {
            animations.cancel()
            item.resetClickListener.invoke()
            Log.d("INFO", "clicked inside  bind")
        }
        placeButtonView.setOnClickListener {
            item.placeClickListener()
        }
        aboutButtonView.setOnClickListener {
            item.aboutClickListener()
        }
        menuButtonView.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_menu, item)
        }
    }

    private fun updateAnimations(time: Int, duration: Long, day: Day?) {
        if (day == null || animations.isRunning) {
            return
        }
//        Log.d("INFO", "isAnimationsRunning")
        animations = AnimatorSet()
        animations.playTogether(
            getNewSun(day, time, sunView.y, duration),
            getNewMoon(day, time, moonViewLayout.y, duration),
            getNewSunColor(
                day,
                time,
                (sunView.background as GradientDrawable).color!!.defaultColor,
                duration
            ),
//            getNewMoonColor(
//                viewModel.day,
//                time,
//                (moonView.background as GradientDrawable).color!!.defaultColor,
//                duration
//            ),
            getNewSkyColor(
                day, time, (skyView.background as ColorDrawable).color, duration
            ),
            getNewGroundColor(
                day, time, (groundView.background as ColorDrawable).color, duration
            )
        )
        animations.start()
    }


    private fun getSunPosition(day: Day, time: Int): Float {
        return if (time >= day.dawn.start && time < day.dawn.end) {
            return sceneView.height.toFloat() - getAnimProgress(
                day.dawn.start, day.dawn.end, time
            ) * groundView.height.toFloat()
        } else if (time >= day.sunrise.start && time < day.sunrise.end) {
            return sceneView.height.toFloat() - groundView.height.toFloat() - getAnimProgress(
                day.sunrise.start, day.sunrise.end, time
            ) * sunView.height.toFloat()
        } else if (time >= day.day.start && time < day.solarNoon.start) {
            return skyView.height.toFloat() - sunView.height.toFloat() - getAnimProgress(
                day.day.start, day.solarNoon.start, time
            ) * 0.75F * (skyView.height.toFloat() - sunView.height.toFloat())
        } else if (time >= day.solarNoon.end && time < day.goldenHour.end) {
            return 0.25F * (skyView.height.toFloat() - sunView.height.toFloat()) + getAnimProgress(
                day.solarNoon.end, day.goldenHour.end, time
            ) * 0.75F * (skyView.height.toFloat() - sunView.height.toFloat())
        } else if (time >= day.sunset.start && time < day.sunset.end) {
            return (skyView.height.toFloat() - sunView.height.toFloat()) + getAnimProgress(
                day.sunset.start, day.sunset.end, time
            ) * sunView.height.toFloat()
        } else if (time >= day.dusk.start && time < day.dusk.end) {
            return skyView.height.toFloat() + getAnimProgress(
                day.dusk.start, day.dusk.end, time
            ) * groundView.height.toFloat()
        } else {
            return sceneView.height.toFloat()
        }
    }

    private fun getMoonPosition(day: Day, time: Int): Float {
        return if (time >= day.dusk.start && time <= day.nightEnd.end) {
            return skyView.height.toFloat() - getAnimProgress(
                day.dusk.start, day.nightEnd.end, time
            ) * 0.8F * skyView.height.toFloat()
        } else if (time >= day.nightStart.start && time <= day.dawn.end) {
            return 0.2F * skyView.height.toFloat() + getAnimProgress(
                day.nightStart.start, day.dawn.end, time
            ) * 0.8F * skyView.height.toFloat()
        } else {
            return return sceneView.height.toFloat()
        }
    }

    fun getMovementAnimator(
        view: View, startPos: Float, endPos: Float, dur: Long = ANIM_DURATION
    ): ValueAnimator {
        return ValueAnimator.ofFloat(
            startPos, endPos
        ).apply {
            interpolator = AccelerateInterpolator()
            duration = dur
            addUpdateListener { animator ->
                view.y = animator.animatedValue as Float
            }
        }
    }

    private fun getViewColorAnimator(
        view: View, startColor: Int, endColor: Int, dur: Long = ANIM_DURATION
    ): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(), startColor, endColor
        ).apply {
            duration = dur
            addUpdateListener { animator ->
                view.setBackgroundColor(
                    animator.animatedValue as Int
                )
            }
        }
    }

    private fun getDrawableColorAnimator(
        view: View, startColor: Int, endColor: Int, animDuration: Long = ANIM_DURATION
    ): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(), startColor, endColor
        ).apply {
            duration = animDuration
            addUpdateListener { animator ->
                (view.background as GradientDrawable).setColor(
                    animator.animatedValue as Int
                )
            }
        }
    }

    private fun getCloudAnimator(
    ): ValueAnimator {
        return ValueAnimator.ofFloat(
            -cloudView.width.toFloat(), sceneView.width.toFloat()
        ).apply {
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
            } else {
                sunGoldenHour2Color
            }
        } else if (time >= day.sunset.start && time < day.sunset.end) {
            s = day.goldenHour.start
            e = day.goldenHour.end
            l = e - s
            if (time >= s && time < s + l * 0.33) {
                sunSunsetColor1
            } else if (time >= s + l * 0.33 && time < s + l * 0.66) {
                sunSunsetColor2
            } else {
                sunSunsetColor3
            }
        } else {
            sunDayColor
        }
    }

    private fun getMoonColor(day: Day, time: Int): Int {
        return if ((time >= day.nightStart.start && time < day.nightStart.end) || (time >= day.nightEnd.start && time < day.nightEnd.end)) {
            moonColorBright
        } else {
            moonColor
        }
    }

    private fun getGroundColor(day: Day, time: Int): Int {
        return if ((time >= day.nightStart.start && time < day.nightStart.end) || (time >= day.nightEnd.start && time < day.nightEnd.end)) {
            groundColorNight
        } else {
            groundColor
        }
    }

    private fun getSkyColor(day: Day, time: Int): Int {
        var s = 0
        var e = 0
        var l = 0
        return if (time >= day.dawn.start && time < day.dawn.end) {
            s = day.dawn.start
            e = day.dawn.end
            l = e - s
            if (time >= s && time < s + l * 0.2) {
                skyDawn1Color
            } else if (time >= s + l * 0.2 && time < s + l * 0.4) {
                skyDawn2Color
            } else if (time >= s + l * 0.4 && time < s + l * 0.6) {
                skyDawn3Color
            } else if (time >= s + l * 0.6 && time < s + l * 0.8) {
                skyDawn4Color
            } else {
                skyDawn5Color
            }
        } else if (time >= day.sunrise.start && time < day.sunrise.end) {
            s = day.sunrise.start
            e = day.sunrise.end
            l = e - s
            if (time >= s && time < s + l * 0.2) {
                skySunrise1Color
            } else if (time >= s + l * 0.2 && time < s + l * 0.4) {
                skySunrise2Color
            } else if (time >= s + l * 0.4 && time < s + l * 0.6) {
                skySunrise3Color
            } else if (time >= s + l * 0.6 && time < s + l * 0.8) {
                skySunrise4Color
            } else {
                skySunrise5Color
            }
        } else if (time >= day.day.start && time < day.solarNoon.start) {
            s = day.day.start
            e = day.solarNoon.start
            l = e - s
            if (time >= s && time < s + l * 0.25) {
                skyDay1Color
            } else if (time >= s + l * 0.25 && time < s + l * 0.5) {
                skyDay2Color
            } else if (time >= s + l * 0.5 && time < s + l * 0.75) {
                skyDay3Color
            } else {
                skyDay4Color
            }
        } else if (time >= day.solarNoon.end && time < day.day.end) {
            s = day.solarNoon.end
            e = day.day.end
            l = e - s
            if (time >= s && time < s + l * 0.25) {
                skyDay4Color
            } else if (time >= s + l * 0.25 && time < s + l * 0.5) {
                skyDay3Color
            } else if (time >= s + l * 0.5 && time < s + l * 0.75) {
                skyDay2Color
            } else {
                skyDay1Color
            }
        } else if (time >= day.goldenHour.start && time < day.goldenHour.end) {
            s = day.goldenHour.start
            e = day.goldenHour.end
            l = e - s
            if (time >= s && time < s + l * 0.5) {
                skyGoldenHour1Color
            } else {
                skyGoldenHour2Color
            }
        } else if (time >= day.sunset.start && time < day.sunset.end) {
            s = day.sunset.start
            e = day.sunset.end
            l = e - s
            if (time >= s && time < s + l * 0.33) {
                skySunset1Color
            } else if (time >= s + l * 0.33 && time < s + l * 0.66) {
                skySunset2Color
            } else {
                skySunset3Color
            }
        } else if (time >= day.dusk.start && time < day.dusk.end) {
            s = day.dusk.start
            e = day.dusk.end
            l = e - s
            if (time >= s && time < s + l * 0.2) {
                skyDuskColor1
            } else if (time >= s + l * 0.2 && time < s + l * 0.4) {
                skyDuskColor2
            } else if (time >= s + l * 0.4 && time < s + l * 0.6) {
                skyDuskColor3
            } else if (time >= s + l * 0.6 && time < s + l * 0.8) {
                skyDuskColor4
            } else {
                skyDuskColor5
            }
        } else {
            skyNightColor
        }
    }

    private fun getNewMoon(
        day: Day, time: Int, currentPos: Float, duration: Long
    ): ValueAnimator? {
        return getMovementAnimator(
            moonViewLayout, currentPos, getMoonPosition(day, time), duration
        )
    }

    private fun getNewSun(
        day: Day, time: Int, currentPos: Float, duration: Long
    ): ValueAnimator? {
        return getMovementAnimator(
            sunView, currentPos, getSunPosition(day, time), duration
        )
    }

    private fun getNewSunColor(
        day: Day, time: Int, currentColor: Int, duration: Long
    ): ValueAnimator? {
        return getDrawableColorAnimator(
            sunView, currentColor, getSunColor(day, time), duration
        )
    }

    private fun getNewMoonColor(
        day: Day, time: Int, currentColor: Int, duration: Long
    ): ValueAnimator? {
        return getDrawableColorAnimator(
            moonView, currentColor, getMoonColor(day, time), duration
        )
    }

    private fun getNewSkyColor(
        day: Day, time: Int, currentColor: Int, duration: Long
    ): ValueAnimator? {
        return getViewColorAnimator(
            skyView, currentColor, getSkyColor(day, time), duration
        )
    }

    private fun getNewGroundColor(
        day: Day, time: Int, currentColor: Int, duration: Long
    ): ValueAnimator? {
        return getViewColorAnimator(
            groundView, currentColor, getGroundColor(day, time), duration
        )
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int, item: SkyItem) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.sunrise_phase_option -> {
                    animations.cancel()
                    item.menuSunriseClickListener()
                }

                R.id.noon_phase_option -> {
                    animations.cancel()
                    item.menuNoonClickListener()
                }

                R.id.sunset_phase_option -> {
                    animations.cancel()
                    item.menuSunsetClickListener()
                }

                R.id.night_phase_option -> {
                    animations.cancel()
                    item.menuNightClickListener()
                }

                R.id.x1_speed_up_option -> {
                    item.menux1ClickListener()
                }

                R.id.x10_speed_up_option -> {
                    item.menux10ClickListener()
                }

                R.id.x100_speed_up_option -> {
                    item.menux100ClickListener()
                }

                R.id.x1000_speed_up_option -> {
                    item.menux1000ClickListener()
                }

                else -> {}
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    companion object {
        const val ANIM_DURATION = 2000L
        private const val LOG_TAG = "MainActivityTag"
    }
}