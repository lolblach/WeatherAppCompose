package com.lolblach333.weatherappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lolblach333.weatherappcompose.ui.theme.WeatherAppComposeTheme
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("London")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var weatherResult by remember { mutableStateOf<WeatherResponse?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (weatherResult != null) {
                Text(text = "Temp in $name is ${weatherResult?.current?.last_updated_epoch}")
            } else {
                Text(text = "Temp in $name is unknown")
            }
        }
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val scope = rememberCoroutineScope()

            Button(
                onClick = {
                    getResult(name, scope) {
                        weatherResult = it


                    }
                }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Refresh")
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
