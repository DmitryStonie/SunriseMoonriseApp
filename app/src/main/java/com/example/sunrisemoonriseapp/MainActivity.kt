package com.example.sunrisemoonriseapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.TypedArray
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

        sceneView.setOnClickListener {
            if (!viewModel.isAnimationPlaying) {
                viewModel.startAnimators()
            }
        }

        sceneView.viewTreeObserver.addOnGlobalLayoutListener(
            OnGlobalLayoutListener {
                if (viewModel.sunAnimator == null) {
                    viewModel.sunAnimator = DayAnimation(
                        viewModel.state,
                        *getSunAnimations()
                    )
                    viewModel.moonAnimator = DayAnimation(
                        viewModel.state,
                        *getMoonAnimations()
                    )
                    viewModel.sunColorAnimator = DayAnimation(
                        viewModel.state,
                        *getSunColorAnimations()
                    )
                    viewModel.moonColorAnimator = DayAnimation(
                        viewModel.state,
                        *getMoonColorAnimations()
                    )
                    viewModel.skyColorAnimator = DayAnimation(
                        viewModel.state,
                        *getSkyColorAnimations()
                    )
                    viewModel.groundColorAnimator = DayAnimation(
                        viewModel.state,
                        *getGroundColorAnimations()
                    )
                } else if (viewModel.sunAnimator?.isCancelled == true) {
                    viewModel.sunAnimator?.changeAnimations(
                        *getSunAnimations(
                            viewModel.sunAnimator?.animationFraction ?: 0.0F
                        )
                    )
                    viewModel.moonAnimator?.changeAnimations(
                        *getMoonAnimations(
                            viewModel.moonAnimator?.animationFraction ?: 0.0F
                        )
                    )
                    viewModel.sunColorAnimator?.changeAnimations(
                        *getSunColorAnimations(
                            viewModel.sunColorAnimator?.animationFraction ?: 0.0F
                        )
                    )
                    viewModel.moonColorAnimator?.changeAnimations(
                        *getMoonColorAnimations(
                            viewModel.moonColorAnimator?.animationFraction ?: 0.0F
                        )
                    )
                    viewModel.skyColorAnimator?.changeAnimations(
                        *getSkyColorAnimations(
                            viewModel.skyColorAnimator?.animationFraction ?: 0.0F
                        )
                    )
                    viewModel.groundColorAnimator?.changeAnimations(
                        *getGroundColorAnimations(
                            viewModel.groundColorAnimator?.animationFraction ?: 0.0F
                        )
                    )
                    viewModel.startAnimators()
                }
            }
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "MainActivity changed arientation")
        if (viewModel.isAnimationPlaying) {
            viewModel.stopAnimators()
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

    private fun getSunAnimations(animationFraction: Float = 0.0F): Array<ValueAnimator?> {
        return arrayOf(
            getMovementAnimator(
                sunView,
                getSunPosition(AnimationState.DAY) + animationFraction * (getSunPosition(
                    AnimationState.SUNSET
                ) - getSunPosition(AnimationState.DAY)),
                getSunPosition(AnimationState.SUNSET),
                animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
            ),
            getMovementAnimator(
                sunView,
                getSunPosition(AnimationState.SUNSET) + animationFraction * (getSunPosition(
                    AnimationState.NIGHT
                ) - getSunPosition(AnimationState.SUNSET)),
                getSunPosition(AnimationState.NIGHT),
                animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
            ),
            getMovementAnimator(
                sunView,
                getSunPosition(AnimationState.NIGHT) + animationFraction * (getSunPosition(
                    AnimationState.SUNRISE
                ) - getSunPosition(AnimationState.NIGHT)),
                getSunPosition(AnimationState.SUNRISE),
                animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
            ),
            getMovementAnimator(
                sunView,
                getSunPosition(AnimationState.SUNRISE) + animationFraction * (getSunPosition(
                    AnimationState.DAY
                ) - getSunPosition(AnimationState.SUNRISE)),
                getSunPosition(AnimationState.DAY),
                animDuration = (ANIM_DURATION * (1 - animationFraction)).toLong()
            )
        )
    }

    private fun getMoonAnimations(animationFraction: Float = 0.0F): Array<ValueAnimator?> {
        return arrayOf(
            null,
            getMovementAnimator(
                moonView,
                getMoonPosition(AnimationState.SUNSET) + animationFraction * (getMoonPosition(
                    AnimationState.NIGHT
                ) - getMoonPosition(AnimationState.SUNSET)),
                getMoonPosition(AnimationState.NIGHT)
            ),
            getMovementAnimator(
                moonView,
                getMoonPosition(AnimationState.NIGHT) + animationFraction * (getMoonPosition(
                    AnimationState.SUNRISE
                ) - getMoonPosition(AnimationState.NIGHT)),
                getMoonPosition(AnimationState.SUNRISE)
            ),
            null
        )
    }

    private fun getSunColorAnimations(animationFraction: Float = 0.0F): Array<ValueAnimator?> {
        return arrayOf(
            getDrawableColorAnimator(
                sunView,
                sundayColor,
                sunsetColor
            ),
            null,
            getDrawableColorAnimator(
                sunView,
                sunsetColor,
                sunriseColor
            ),
            getDrawableColorAnimator(
                sunView,
                sunriseColor,
                sundayColor
            )
        )
    }

    private fun getMoonColorAnimations(animationFraction: Float = 0.0F): Array<ValueAnimator?> {
        return arrayOf(
            null,
            getDrawableColorAnimator(
                moonView,
                moonColor,
                moonColorBright
            ),
            getDrawableColorAnimator(
                moonView,
                moonColorBright,
                moonColor
            ),
            null
        )
    }

    private fun getSkyColorAnimations(animationFraction: Float = 0.0F): Array<ValueAnimator?> {
        return arrayOf(
            getViewColorAnimator(
                skyView,
                skyDayColor,
                skySunsetColor
            ),
            getViewColorAnimator(
                skyView,
                skySunsetColor,
                skyNightColor
            ),
            getViewColorAnimator(
                skyView,
                skyNightColor,
                skySunriseColor
            ),
            getViewColorAnimator(
                skyView,
                skySunriseColor,
                skyDayColor
            )
        )
    }

    private fun getGroundColorAnimations(animationFraction: Float = 0.0F): Array<ValueAnimator?> {
        return arrayOf(
            null,
            getViewColorAnimator(
                groundView,
                groundColor,
                groundColorNight
            ),
            getViewColorAnimator(
                groundView,
                groundColorNight,
                groundColor
            ),
            null
        )
    }

    companion object {
        const val ANIM_DURATION = 2000L
//        private const val ANIM_DURATION_SHORT = 1000L
    }
}
