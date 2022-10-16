package com.lolblach333.weatherappcompose

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
}
