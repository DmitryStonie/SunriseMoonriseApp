package com.dmitrystonie.sunrisemoonriseapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.viewholders.DayInfoViewHolder
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.viewholders.SkyViewHolder
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.viewholders.WeatherInfoViewHolder
import com.dmitrystonie.sunrisemoonriseapp.ui.screens.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SkyViewTest {

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
    fun skyViewHolder_exists() {
        onView(withId(R.id.eventsRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<SkyViewHolder>(withId(R.id.scene))
            )
    }
    @Test
    fun temperatureView_isVisible() {
        onView(withId(R.id.eventsRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
            )
        onView(withId(R.id.temperatureText)).check(matches(isDisplayed()))
    }
    @Test
    fun weatherName_isVisible() {
        onView(withId(R.id.eventsRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
            )
        onView(withId(R.id.weatherName)).check(matches(isDisplayed()))
    }
    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }

}