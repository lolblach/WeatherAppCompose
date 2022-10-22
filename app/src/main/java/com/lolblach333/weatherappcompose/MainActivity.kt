package com.lolblach333.weatherappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.lolblach333.weatherappcompose.model.WeatherResponseHistory
import com.lolblach333.weatherappcompose.screens.Calendar
import com.lolblach333.weatherappcompose.screens.ListItems
import com.lolblach333.weatherappcompose.screens.MainScreen
import com.lolblach333.weatherappcompose.screens.TabLayout
import com.lolblach333.weatherappcompose.screens.TextField
import com.lolblach333.weatherappcompose.ui.theme.WeatherAppComposeTheme
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "85ccbfd424704d8d817172205221610"
private const val TIMEOUT = 15L

private val apiService by lazy { provideApiService() }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                Image(
                    painter = painterResource(id = R.drawable.day),
                    contentDescription = "im1",
                    Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    var weatherHourResult by remember { mutableStateOf<WeatherResponseHistory?>(null) }
                    var weatherResult by remember { mutableStateOf<WeatherResponse?>(null) }
                    var cityResult by remember { mutableStateOf("London") }
                    var dateResult by remember { mutableStateOf("2022-10-10") }
                    val scope = rememberCoroutineScope()

                    MainScreen(
                        cityResult,
                        weatherResult?.current?.temp_c ?: "0'C",
                        weatherResult?.current?.condition?.text ?: "Cloudy"
                    )
                    TextField { cityResult = it }
                    Calendar { dateResult = it }
                    TabLayout()

                    weatherHourResult?.forecast?.forecastday?.takeIf { it.isNotEmpty() }?.let {
                        ListItems(it)
                    }

                    getResult(cityResult, scope) {
                        weatherResult = it
                    }
                    getHourResult(cityResult, scope, dateResult) {
                        weatherHourResult = it
                    }

                }
            }
        }
    }
}

private fun getResult(city: String, scope: CoroutineScope, result: (WeatherResponse) -> Unit) {
    scope.launch {
        val response = apiService.getWeather(API_KEY, city, "no").body()
        Log.e("RESULT", response.toString())
        response?.let(result)
    }
}

private fun getHourResult(
    city: String,
    scope: CoroutineScope,
    date: String,
    resultHour: (WeatherResponseHistory) -> Unit
) {
    scope.launch {
        val response = apiService.getHourWeather(API_KEY, city, date).body()
        response?.let(resultHour)
    }
}

private fun provideApiService(): ApiService {
    val okHttpClientBuilder = OkHttpClient.Builder()

    okHttpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)

    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }.let(okHttpClientBuilder::addInterceptor)

    return Retrofit.Builder()
        .baseUrl("http://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClientBuilder.build())
        .build()
        .create(ApiService::class.java)
}
