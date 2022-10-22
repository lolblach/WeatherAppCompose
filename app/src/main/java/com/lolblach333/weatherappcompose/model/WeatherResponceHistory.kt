package com.lolblach333.weatherappcompose.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseHistory(
    @SerializedName("location")
    val location : Location?,
    @SerializedName("forecast")
    val forecast: Forecast?
)

data class Location(
    @SerializedName("name")
    val name: String?,
    @SerializedName("localtime")
    val localtime: String?
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>?
)

data class Forecastday(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: Day?,
    @SerializedName("hour")
    val hour: List<Hour>?
)

data class Day(
    @SerializedName("condition")
    val condition: Condition?
)

data class Condition(
    @SerializedName("text")
    val text: String?,
    @SerializedName("icon")
    val icon: String?
)

data class Hour(
    @SerializedName("time")
    val time: String?,
    @SerializedName("temp_c")
    val temp_c: String?,
    @SerializedName("condition")
    val condition: ConditionHour?
)

data class ConditionHour(
    @SerializedName("text")
    val text: String?,
    @SerializedName("icon")
    val icon: String?
)