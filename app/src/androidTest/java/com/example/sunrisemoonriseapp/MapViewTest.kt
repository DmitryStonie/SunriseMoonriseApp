package com.example.sunrisemoonriseapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.sunrisemoonriseapp.presentation.recyclerview.viewholders.DayInfoViewHolder
import com.example.sunrisemoonriseapp.ui.screens.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MapViewTest {

    @get:Rule
    val activityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun mapView_exists() {
        onView(withId(R.id.eventsRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
            )
        onView(withId(R.id.placeButton)).perform(click())
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

}