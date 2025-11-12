package com.example.proyekuts.ui.screens.weather

import androidx.annotation.RawRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.proyekuts.R

data class WeatherUiState(
    @RawRes
    val weatherAnimation: Int = R.raw.weather_partly_cloudy,
    val location: String = "Bandung, ID",
    val temperature: Int = 28,
    val condition: String = "Cerah Berawan",
    val tempHigh: Int = 32,
    val tempLow: Int = 24,
    val humidity: Int = 85,
    val windSpeed: Int = 10,
    val hourlyForecasts: List<HourlyForecast> = listOf(
        HourlyForecast("12:00", Icons.Default.WbCloudy, 29),
        HourlyForecast("13:00", Icons.Default.WbSunny, 31),
        HourlyForecast("14:00", Icons.Default.WbSunny, 28, isSelected = true),
        HourlyForecast("15:00", Icons.Default.WbCloudy, 27),
        HourlyForecast("16:00", Icons.Default.WaterDrop, 26)
    ),
    val dailyForecasts: List<DailyForecast> = listOf(
        DailyForecast("Besok", Icons.Default.WaterDrop, 30, 23),
        DailyForecast("Senin", Icons.Default.WbCloudy, 29, 24),
        DailyForecast("Selasa", Icons.Default.WbSunny, 32, 25),
        DailyForecast("Rabu", Icons.Default.WbSunny, 33, 25),
        DailyForecast("Kamis", Icons.Default.WaterDrop, 28, 23),
        DailyForecast("Jumat", Icons.Default.WbCloudy, 29, 24)
    )
)

// Data class untuk prakiraan per jam
data class HourlyForecast(
    val time: String,
    val icon: ImageVector,
    val temperature: Int,
    val isSelected: Boolean = false
)

// Data class untuk prakiraan harian
data class DailyForecast(
    val day: String,
    val icon: ImageVector,
    val tempHigh: Int,
    val tempLow: Int
)

// State holder
class WeatherState {
    var uiState by mutableStateOf(WeatherUiState())
        private set
}