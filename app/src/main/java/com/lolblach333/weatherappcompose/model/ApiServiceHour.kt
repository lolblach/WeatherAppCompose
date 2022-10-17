package com.lolblach333.weatherappcompose.model

import com.lolblach333.weatherappcompose.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceHour {

    @GET("history.json")
    suspend fun getHourWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("dt") api: String
    ): Response<WeatherResponseHistory>
}
//http://api.weatherapi.com/v1/history.json?key=85ccbfd424704d8d817172205221610&q=London&dt=2022-10-20