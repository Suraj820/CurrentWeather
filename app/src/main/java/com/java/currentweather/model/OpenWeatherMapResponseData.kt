package com.java.currentweather.model

import com.squareup.moshi.Json


//
// Created by Suraj on 25/03/22.
data class OpenWeatherMapResponseData(
    @field:Json(name = "name")
    val locationName: String,
    val weather: List<OpenWeatherMapWeatherData>
)
data class OpenWeatherMapWeatherData(
    @field:Json(name = "main")
    val status: String,
    val description: String,
    val icon: String
)
