package com.example.sunrisemoonriseapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.sunrisemoonriseapp.presentation.recyclerview.viewholders.DayInfoViewHolder
import com.example.sunrisemoonriseapp.ui.screens.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DayInfoViewTest {

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
    fun dayInfoViewHolder_exists() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
    }

    @Test
    fun dayInfoCloudinessIconWindSpeedText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.cloudinessIcon)).check(isCompletelyAbove(withId(R.id.windSpeedText)))
    }
    @Test
    fun dayInfoCloudinessIconCloudinessText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.cloudinessIcon)).check(isCompletelyLeftOf(withId(R.id.cloudinessText)))
    }
    @Test
    fun dayInfoCloudinessTextSpeedText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.cloudinessText)).check(isCompletelyAbove(withId(R.id.windSpeedText)))
    }

    @Test
    fun dayInfoWindSpeedTextWindDegreeText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.windSpeedText)).check(isCompletelyAbove(withId(R.id.windDegreeText)))
    }

    @Test
    fun dayInfoCloudinessTextCompasView_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.cloudinessText)).check(isCompletelyLeftOf(withId(R.id.compassView)))
    }

    @Test
    fun dayInfoWindSpeedTextCompasView_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.windSpeedText)).check(isCompletelyLeftOf(withId(R.id.compassView)))
    }

    @Test
    fun dayInfoWindDegreeTextCompasView_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.windDegreeText)).check(isCompletelyLeftOf(withId(R.id.compassView)))
    }

    @Test
    fun dayInfoDayLengthTitleSunriseTitle_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.dayLengthTitle)).check(isCompletelyAbove(withId(R.id.sunriseTitle)))
    }

    @Test
    fun dayInfodayLengthTitleDayLengthText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.dayLengthTitle)).check(isCompletelyLeftOf(withId(R.id.dayLengthText)))
    }

    @Test
    fun dayInfoSunriseTitleSunsetTitle_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.sunriseTitle)).check(isCompletelyAbove(withId(R.id.sunsetTitle)))
    }

    @Test
    fun dayInfoSunriseTitleSunriseText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.sunriseTitle)).check(isCompletelyLeftOf(withId(R.id.sunriseText)))
    }
    @Test
    fun dayInfoSunsetTitleSunsetText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.sunsetTitle)).check(isCompletelyLeftOf(withId(R.id.sunsetText)))
    }
    @Test
    fun dayInfoDayLengthTextSunriseText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.dayLengthText)).check(isCompletelyAbove(withId(R.id.sunriseText)))
    }
    @Test
    fun dayInfoSunriseTextSunsetText_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.sunriseText)).check(isCompletelyAbove(withId(R.id.sunsetText)))
    }

    @Test
    fun dayInfoWindViewSunView_notOverlaps() {
        onView(withId(R.id.eventsRecyclerView)).perform(
            RecyclerViewActions.scrollTo<DayInfoViewHolder>(withId(R.id.dayInfo))
        )
        onView(withId(R.id.windView)).check(isCompletelyLeftOf(withId(R.id.sunView)))
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }
}