package com.lolblach333.weatherappcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lolblach333.weatherappcompose.model.Forecastday
import com.lolblach333.weatherappcompose.ui.theme.BlueLight

@Composable
fun MainScreen(city: String, temp: String, weather: String) {
    Column(
        modifier = Modifier
            .padding(top = 30.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = city,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(blurRadius = 2f)
                    )
                )

                Text(
                    text = temp,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(blurRadius = 3f)
                    )
                )

                Text(
                    text = weather,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(blurRadius = 3f)
                    )
                )
            }
        }
    }
}

@Composable
fun TabLayout() {
    val tabList = listOf("HOURS", "DAYS")
    Column(
        modifier = Modifier
            .padding(top = 15.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
            .clip(
                RoundedCornerShape(5.dp)
            )
    ) {
        TabRow(
            selectedTabIndex = 0,
            indicator = {},
            backgroundColor = BlueLight
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {

                    },
                    text = {
                        Text(
                            text = text,
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(blurRadius = 3f)
                            )

                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TextField(onTextSelected: (String) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            Modifier.fillMaxWidth(0.7f)
        )
        Button(
            onClick = {
                onTextSelected(textState.value.text)
            },
            Modifier.fillMaxWidth(0.3f)
        ) {
        }
    }
}

@Composable
fun Calendar(onDateSelected: (String) -> Unit) {
    val dateState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = dateState.value,
            onValueChange = { dateState.value = it },
            Modifier.fillMaxWidth(0.7f)
        )
        Button(
            onClick = {
                onDateSelected(dateState.value.text)
            },
            Modifier.fillMaxWidth(0.3f)
        ) {
        }
    }
}

@Composable
fun ListItem(currentHour: String, weatherState: String, temp: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Text(text = currentHour)
                Text(
                    text = weatherState,
                    color = Color.White
                )
            }
            Text(
                text = temp,
                color = Color.White,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(text = "Я картинка")
        }

    }
}

@Composable
fun ListItems(list: List<Forecastday>) {
    LazyColumn {
        list[0].hour?.forEach {
            val time = it.time
            val weatherState = it.condition?.text
            val temp = it.temp_c

            if (time != null && weatherState != null && temp != null) {
                item {
                    ListItem(time, weatherState, temp)
                }
            }
        }
    }
}
