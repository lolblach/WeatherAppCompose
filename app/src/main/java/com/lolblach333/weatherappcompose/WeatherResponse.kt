package com.lolblach333.weatherappcompose

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location")
    val location: Location?,
    @SerializedName("current")
    val current: Current?
)

data class Location(
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    // TODO Add more fields
)

data class Current(
    @SerializedName("last_updated_epoch")
    val last_updated_epoch: String?,
    // TODO Add more fields
)
