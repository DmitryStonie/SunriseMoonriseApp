package com.example.sunrisemoonriseapp


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrisemoonriseapp.recyclerview.RecyclerAdapter
import com.example.sunrisemoonriseapp.recyclerview.items.DayInfoItem
import com.example.sunrisemoonriseapp.recyclerview.items.SkyItem
import com.example.sunrisemoonriseapp.recyclerview.items.WeatherInfoItem
import com.example.sunrisemoonriseapp.ui.AboutFragment
import com.example.sunrisemoonriseapp.ui.PlaceFragment
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var eventsRecyclerView: RecyclerView

    private val viewModel by viewModels<MainScreenViewModel>()
    lateinit var mainView: View


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
            viewModel.getWeatherInfo(it.latitude, it.longitude)
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
            viewModel.saveWeather()
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
