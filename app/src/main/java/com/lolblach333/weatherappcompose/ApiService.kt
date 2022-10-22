package com.lolblach333.weatherappcompose

import com.lolblach333.weatherappcompose.model.WeatherResponseHistory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("api") api: String
    ): Response<WeatherResponse>

    @GET("history.json")
    suspend fun getHourWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("dt") dt: String
    ): Response<WeatherResponseHistory>
}
