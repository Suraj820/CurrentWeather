package com.java.currentweather

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.java.currentweather.model.OpenWeatherMapResponseData
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    private val weatherApiService by lazy {
        retrofit.create(OpenWeatherMapService::class.java)
    }

    private val titleView: TextView
            by lazy { findViewById(R.id.main_title) }
    private val statusView: TextView
            by lazy { findViewById(R.id.main_status) }
    private val descriptionView: TextView
            by lazy { findViewById(R.id.main_description) }
    private val weatherIconView: ImageView
            by lazy { findViewById(R.id.main_weather_icon) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ai: ApplicationInfo = applicationContext.packageManager.getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["ApiKey"]

        weatherApiService
            .getWeather("Mumbai", value.toString())
            .enqueue(object : Callback<OpenWeatherMapResponseData> {
                override fun onFailure(call: Call<OpenWeatherMapResponseData>, t: Throwable) {
                }
                override fun onResponse(call: Call<OpenWeatherMapResponseData>, response: Response<OpenWeatherMapResponseData>) = handelResponse(response)
            })
    }

    private fun handelResponse(response: Response<OpenWeatherMapResponseData>) =
        if (response.isSuccessful) {
            response.body()?.let { validResponse ->
                handelValidResponse(validResponse)
            } ?: Unit
        } else {
        }

    private fun handelValidResponse(response: OpenWeatherMapResponseData) {
        titleView.text = response.locationName
        response.weather.firstOrNull()?.let { weather ->
            statusView.text = weather.status
            descriptionView.text = weather.description
            Glide.with(this).load("https://openweathermap.org/img/wn/${weather.icon}@2x.png")
                .centerInside()
                .into(weatherIconView)
        }
    }
}

