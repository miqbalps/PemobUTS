package com.example.proyekuts.ui.screens.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyekuts.ui.theme.ProyekUTSTheme

// Composable utama
@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    val stateHolder = remember { WeatherState() }
    val state = stateHolder.uiState
    val darkBlueBg = Color(0xFF013773)
    val cardBlueBg = Color(0xFF1A4677)
    val darkBlueGradient = Brush.verticalGradient(
        colors = listOf(darkBlueBg, Color(0xFF002145))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(darkBlueGradient)
    ) {
        // 1. Top Bar Kustom
        WeatherTopBar(location = state.location)

        // 2. Konten yang Dapat Di-scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 3. Kartu Cuaca Utama
            MainWeatherCard(state = state, backgroundColor = cardBlueBg)

            // 4. Prakiraan Per Jam
            HourlyForecastSection(forecasts = state.hourlyForecasts, cardColor = cardBlueBg)

            // 5. Prakiraan Harian
            DailyForecastSection(forecasts = state.dailyForecasts)
        }
    }
}

// 1. Top Bar
@Composable
private fun WeatherTopBar(location: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Cuaca",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Lokasi",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = location,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Cari",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}

// 3. Kartu Cuaca Utama
@Composable
private fun MainWeatherCard(state: WeatherUiState, backgroundColor: Color) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(state.weatherAnimation)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ikon Cerah Berawan (Sun + Cloud)
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever, // Ulangi animasi selamanya
                modifier = Modifier.size(250.dp)
            )

            Text(
                text = "${state.temperature}°",
                color = Color.White,
                fontSize = 90.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = state.condition,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "H:${state.tempHigh}° L:${state.tempLow}°",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp
            )

            Divider(
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherDetailItem(label = "Humidity", value = "${state.humidity}%")
                WeatherDetailItem(label = "Wind Speed", value = "${state.windSpeed} km/h")
            }
        }
    }
}

// Helper untuk detail di Kartu Utama
@Composable
private fun WeatherDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// 4. Bagian Prakiraan Per Jam
@Composable
private fun HourlyForecastSection(forecasts: List<HourlyForecast>, cardColor: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Kondisi Per Jam",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(forecasts) { forecast ->
                HourlyForecastItem(forecast = forecast, selectedColor = cardColor)
            }
        }
    }
}

// Item untuk Prakiraan Per Jam
@Composable
private fun HourlyForecastItem(forecast: HourlyForecast, selectedColor: Color) {
    val backgroundColor = if (forecast.isSelected) selectedColor else Color.Transparent

    Column(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = forecast.time,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )
        Icon(
            imageVector = forecast.icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = "${forecast.temperature}°",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// 5. Bagian Prakiraan Harian
@Composable
private fun DailyForecastSection(forecasts: List<DailyForecast>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Prakiraan Cuaca",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            forecasts.forEach { forecast ->
                DailyForecastItem(forecast = forecast)
            }
        }
    }
}

// Item untuk Prakiraan Harian
@Composable
private fun DailyForecastItem(forecast: DailyForecast) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = forecast.day,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = forecast.icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "H:${forecast.tempHigh}°  L:${forecast.tempLow}°",
            color = Color.White.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(110.dp) // Beri lebar agar rapi
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF013674)
@Composable
fun WeatherScreenPreview() {
    ProyekUTSTheme {
        WeatherScreen()
    }
}