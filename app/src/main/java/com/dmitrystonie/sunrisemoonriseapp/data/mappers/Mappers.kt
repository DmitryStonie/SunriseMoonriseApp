package com.dmitrystonie.sunrisemoonriseapp.data.mappers

import com.dmitrystonie.sunrisemoonriseapp.data.database.entities.WeatherEntity
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Moon
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Weather
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.moon.MoonDate
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.openweather.OpenWeatherApiResponse

fun OpenWeatherApiResponse.toWeather(date: String = ""): Weather {
    return Weather(
        date = date,
        name = weather[0].main,
        description = weather[0].description,
        cloudiness = clouds.all,
        temperature = main.temp,
        temperatureFeelsLike = main.feelsLike,
        temperatureMin = main.tempMin,
        temperatureMax = main.tempMax,
        windSpeed = wind.speed,
        windDegree = wind.deg,
        visibility = visibility,
        humidity = main.humidity,
        pressure = main.pressure,
        pressureSeaLevel = main.seaLevel,
        pressureGroundLevel = main.groundLevel
    )
}

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        date,
        name,
        description,
        cloudiness,
        temperature,
        temperatureFeelsLike,
        temperatureMin,
        temperatureMax,
        windSpeed,
        windDegree,
        visibility,
        humidity,
        pressure,
        pressureSeaLevel,
        pressureGroundLevel
    )
}

fun WeatherEntity.toWeather(): Weather{
    return Weather(
        date,
        name,
        description,
        cloudiness,
        temperature,
        temperatureFeelsLike,
        temperatureMin,
        temperatureMax,
        windSpeed,
        windDegree,
        visibility,
        humidity,
        pressure,
        pressureSeaLevel,
        pressureGroundLevel
    )
}

fun MoonDate.toMoon(date: String): Moon{
    return Moon(
        date,
        moon.getOrNull(0) ?: "",
        index,
        age,
        phase,
        distance,
        illumination,
        angularDiameter,
        distanceToSun,
        sunAngularDiameter
    )
}