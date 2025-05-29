package com.example.sunrisemoonriseapp.mappers

import com.example.sunrisemoonriseapp.database.core.entities.WeatherEntity
import com.example.sunrisemoonriseapp.entities.Weather
import com.example.sunrisemoonriseapp.retrofit.openweather.OpenWeatherApiResponse

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