package com.example.sunrisemoonriseapp

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.sunrisemoonriseapp.day.Day
import com.example.sunrisemoonriseapp.day.Day.Companion.NIGHT_START_START_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.DAWN_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.SUNRISE_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.DAY_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.SOLAR_NOON_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.GOLDEN_HOUR_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.SUNSET_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.DUSK_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.NIGHT_END_START_DEFAULT
import com.example.sunrisemoonriseapp.day.Day.Companion.NIGHT_END_END_DEFAULT
import com.example.sunrisemoonriseapp.day.DayState
import com.example.sunrisemoonriseapp.repository.MoonRepository
import com.example.sunrisemoonriseapp.repository.SunRepository
import com.example.sunrisemoonriseapp.retrofit.moon.MoonDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(val sunRepository: SunRepository, val moonRepository: MoonRepository) : ViewModel() {

    lateinit var day: Day
    fun isDayInitialized() = ::day.isInitialized
    private var systemTime = 0L
    var timeMultiplier = 1
    var byTime = true
    var isRunning = true

    val time: MutableLiveData<Long> by lazy {
        systemTime = System.currentTimeMillis()
        viewModelScope.launch {
            while(true){
                delay(1000L / timeMultiplier)
                if(isRunning) {
                    systemTime += 1000
                    time.postValue(systemTime)
//                    Log.d("INFO", "time ${time.value}")
                }
            }
        }
        MutableLiveData<Long>(systemTime) }

    fun getDayInfo(latitude: String, longitude: String) : LiveData<Day?> {
        return liveData {
            val data = sunRepository.getSunrise(latitude, longitude)
            if(data == null){
                Log.d("INFO", "Got null data")
                null
            }
            Log.d("INFO", "Got data")
            val values = arrayListOf(
                NIGHT_START_START_DEFAULT,
                if(data?.dawn != null) data.dawn.getSecondsValue() else DAWN_DEFAULT,
                if(data?.sunrise != null) data.sunrise.getSecondsValue() else SUNRISE_DEFAULT,
                if(data?.sunrise != null) data.sunrise.getSecondsValue() + 240 else DAY_DEFAULT,
                if(data?.solarNoon != null) data.solarNoon.getSecondsValue() else SOLAR_NOON_DEFAULT,
                if(data?.goldenHour != null) data.goldenHour.getSecondsValue() else GOLDEN_HOUR_DEFAULT,
                if(data?.sunset != null) data.sunset.getSecondsValue() else SUNSET_DEFAULT,
                if(data?.sunset != null) data.sunset.getSecondsValue() + 240 else DUSK_DEFAULT,
                if(data?.dusk != null) data.dusk.getSecondsValue() else NIGHT_END_START_DEFAULT,
                NIGHT_END_END_DEFAULT
            )
            day = Day(
                nightStart = DayState(values[0], values[1] ),
                dawn = DayState(values[1], values[2]),
                sunrise = DayState(values[2], values[3]),
                day = DayState(values[3], values[5]),
                solarNoon = DayState(values[4], values[4]),
                goldenHour = DayState(values[5], values[6]),
                sunset = DayState(values[6], values[7]),
                dusk = DayState(values[7], values[8]),
                nightEnd = DayState(values[8], values[9])
            )
            emit(day)
        }
    }
    fun setTime(value: Int){
        systemTime = System.currentTimeMillis()
        time.value = systemTime - (getSecondsFromToday() - value)* 1000
        systemTime = time.value!!
    }
    fun resetTime(){
        systemTime = System.currentTimeMillis()
        time.value = systemTime
    }

    fun setSunrise(){
        systemTime = System.currentTimeMillis()
        time.value = systemTime - (getSecondsFromToday() - day.sunrise.start )* 1000
        systemTime = time.value!!
    }

    fun setSunset(){
        systemTime = System.currentTimeMillis()
        time.value = systemTime - (getSecondsFromToday() - day.sunset.start) * 1000
        systemTime = time.value!!
    }
    fun setNoon(){
        systemTime = System.currentTimeMillis()
        time.value = systemTime - (getSecondsFromToday() - day.solarNoon.start ) * 1000
        systemTime = time.value!!
    }
    fun setNight(){
        systemTime = System.currentTimeMillis()
        time.value = systemTime - getSecondsFromToday() * 1000
        systemTime = time.value!!
    }

    private fun getSecondsFromToday(): Int{
        val date = Date(systemTime)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(
            Calendar.SECOND)
    }

    fun setByTime(bool: Boolean, time: Long) {
        byTime = bool
        viewModelScope.launch {
            isRunning = false
            delay(time)
            isRunning = true
        }
    }

    fun getMoonInfo(): LiveData<MoonDate>{
        return liveData {
            val moonDate = moonRepository.getMoon(if(systemTime == 0L) System.currentTimeMillis() / 1000  else systemTime / 1000 )
            emit(moonDate ?: MoonDate(0, "", "", arrayListOf(""), 0, 0.0,"", 0.0F,0.0,0.0,0.0,0.0))
        }
    }




}