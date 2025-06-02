package com.dmitrystonie.sunrisemoonriseapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.assertion.LayoutAssertions.noOverlaps
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.viewholders.WeatherInfoViewHolder
import com.dmitrystonie.sunrisemoonriseapp.ui.screens.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@LargeTest
class WeatherInfoViewTest {

    private var mIdlingResource: IdlingResource? = null

    @Before
    fun registerIdlingResource() {
        val activityScenario: ActivityScenario<*> =
            ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            mIdlingResource = (activity as MainActivity).getIdlingResource();
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource);
        }
    }

    @Test
    fun weatherInfoViewHolder_exists() {
        swipeToWeatherInfoView()
    }

    @Test
    fun feelsLikeTitleTempMaxTitle_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.feelsLikeTitle)).check(isCompletelyAbove(withId(R.id.temperatureMaxTitle)))
    }

    @Test
    fun feelsLikeTitleFeelsLikeText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.feelsLikeTitle)).check(isCompletelyLeftOf(withId(R.id.feelsLikeText)))
    }

    @Test
    fun tempMaxTitleTempMinTitle_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.temperatureMaxTitle)).check(isCompletelyAbove(withId(R.id.temperatureMinTitle)))
    }

    @Test
    fun tempMaxTitleTempMaxText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.temperatureMaxTitle)).check(isCompletelyLeftOf(withId(R.id.temperatureMaxText)))
    }

    @Test
    fun tempMinTitleTempMinText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.temperatureMinTitle)).check(isCompletelyLeftOf(withId(R.id.temperatureMinText)))
    }

    @Test
    fun feelsLikeTextTempMaxText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.feelsLikeText)).check(isCompletelyAbove(withId(R.id.temperatureMaxText)))
    }

    @Test
    fun tempMaxTextTempMinText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.temperatureMaxText)).check(isCompletelyAbove(withId(R.id.temperatureMinText)))
    }

    @Test
    fun humidityTitlePressureTitle_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.humidityTitle)).check(isCompletelyAbove(withId(R.id.pressureTitle)))
    }

    @Test
    fun humidityTitleHumidityText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.humidityTitle)).check(isCompletelyLeftOf(withId(R.id.humidityText)))
    }

    @Test
    fun pressureTitlePressureText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.pressureTitle)).check(isCompletelyLeftOf(withId(R.id.pressureText)))
    }

    @Test
    fun humidityTextPressureText_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.humidityText)).check(isCompletelyAbove(withId(R.id.pressureText)))
    }

    @Test
    fun tempViewAtmosphereView_notOverlaps() {
        swipeToWeatherInfoView()
        onView(withId(R.id.tempView)).check(isCompletelyLeftOf(withId(R.id.atmosphereView)))
    }

    fun swipeToWeatherInfoView() {
        onView(withId(R.id.eventsRecyclerView)).perform(
                RecyclerViewActions.scrollTo<WeatherInfoViewHolder>(withId(R.id.weatherInfo))
            )
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }

}