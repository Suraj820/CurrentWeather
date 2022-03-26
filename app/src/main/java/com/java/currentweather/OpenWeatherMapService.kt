package com.java.currentweather


import com.java.currentweather.model.OpenWeatherMapResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//
// Created by Suraj on 25/03/22.

interface OpenWeatherMapService {
    @GET("weather")
    fun getWeather(
        @Query("q") location: String,
        @Query("appid") token: String
    ) : Call<OpenWeatherMapResponseData>
}

