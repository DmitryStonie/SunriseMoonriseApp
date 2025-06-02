package com.dmitrystonie.sunrisemoonriseapp.ui.screens


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource
import com.dmitrystonie.sunrisemoonriseapp.R
import com.dmitrystonie.sunrisemoonriseapp.presentation.MainScreenViewModel
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.RecyclerAdapter
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.items.DayInfoItem
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.items.SkyItem
import com.dmitrystonie.sunrisemoonriseapp.presentation.recyclerview.items.WeatherInfoItem
import com.dmitrystonie.sunrisemoonriseapp.ui.testing.MessageDelayer
import com.dmitrystonie.sunrisemoonriseapp.ui.testing.SimpleIdlingResource
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MessageDelayer.DelayerCallback  {
    private lateinit var eventsRecyclerView: RecyclerView

    private val viewModel by viewModels<MainScreenViewModel>()
    lateinit var mainView: View

    // The Idling Resource which will be null in production.
    @Nullable
    private var mIdlingResource: SimpleIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("INFO", "MainActivity successfully created $this")
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mainView = findViewById<View>(R.id.mainView)
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView)

        val adapter = RecyclerAdapter(
            arrayListOf(
                SkyItem(
                    {
                        viewModel.byTime = false
                        viewModel.resetTime()
                        Log.d("INFO", "clicl")
                    },
                    {
                        supportFragmentManager.beginTransaction().add(R.id.mainView, PlaceFragment())
                            .addToBackStack("MAP").commit()
                    },
                    {
                        supportFragmentManager.beginTransaction().add(R.id.mainView, AboutFragment())
                            .addToBackStack("ABOUT").commit()
                    },
                    {
                        viewModel.byTime = false
                        viewModel.setSunrise()
                    },
                    {
                        viewModel.byTime = false
                        viewModel.setNoon()
                    },
                    {
                        viewModel.byTime = false
                        viewModel.setSunset()
                    },
                    {
                        viewModel.byTime = false
                        viewModel.setNight()
                    },
                    { viewModel.timeMultiplier = 1 },
                    { viewModel.timeMultiplier = 10 },
                    { viewModel.timeMultiplier = 100 },
                    { viewModel.timeMultiplier = 1000 },
                    viewModel.time,
                    viewModel.dayInfo,
                    viewModel.moonInfo,
                    viewModel.animDuration,
                    viewModel.placeInfo,
                    viewModel.weatherInfo
                ), WeatherInfoItem(viewModel.weatherInfo),
                DayInfoItem(viewModel.dayInfo, viewModel.weatherInfo)
            )
        )
        eventsRecyclerView.layoutManager = LinearLayoutManager(this)
        eventsRecyclerView.adapter = adapter

        viewModel.getPlaceInfo()
        viewModel.getMoonInfo()
        var curDate = -1
        viewModel.placeInfo.observe(this) {
            viewModel.getDayInfo(it.latitude, it.longitude)
            viewModel.getWeatherInfoRemote(it.latitude, it.longitude)
            Log.d("INFO", "Got place $it")
        }
        viewModel.dayInfo.observe(this) { day ->
            val date = Date(viewModel.time.value ?: 0)
            val calendar = Calendar.getInstance()
            calendar.time = date
            curDate = calendar.get(Calendar.DATE)
        }
        viewModel.moonInfo.observe(this) { moon ->
            Log.d("INFO", "Got moon api response $moon")
        }
        viewModel.time.observe(this) {
            val date = Date(it)
            val calendar = Calendar.getInstance()
            calendar.time = date
            if (calendar.get(Calendar.DATE) > curDate && curDate != -1) {
                viewModel.getDayInfo(
                    viewModel.placeInfo.value!!.latitude, viewModel.placeInfo.value!!.longitude
                )
                viewModel.getMoonInfo()
                curDate = calendar.get(Calendar.DATE)
            }
        }
        viewModel.weatherInfo.observe(this) { weather ->
            Log.d("INFO", " got weather $weather")
//            viewModel.saveWeather()
            MessageDelayer.processMessage("work done", this, mIdlingResource);
        }
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            WindowInsetsCompat.CONSUMED
        }

    }
    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    fun getIdlingResource(): IdlingResource {
        Log.d("INFO_ACTIVITY", "requested indlingres")
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource!!
    }

    override fun onDone(text: String?) {
        Log.d("INFO_ACTIVITY", "done try to save")
        viewModel.saveWeather()
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
fun Int.getTimeValueShort(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60

    return String.format(Locale.US, "%02d:%02d", hours, minutes)
}

fun getAnimProgress(start: Int, end: Int, current: Int): Float {
    return (1F * current - start) / (1F * end - start)
}
