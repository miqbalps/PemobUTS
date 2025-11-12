package com.example.proyekuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.proyekuts.ui.screens.SplashScreen
import com.example.proyekuts.ui.theme.ProyekUTSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyekUTSTheme {
                // Gunakan state untuk mengontrol layar mana yang ditampilkan
                var showSplashScreen by remember { mutableStateOf(true) }

                if (showSplashScreen) {
                    // Tampilkan Splash Screen dan ubah state setelah timeout
                    SplashScreen(onTimeout = { showSplashScreen = false })
                } else {
                    // Setelah timeout, tampilkan DashboardScreen yang menjadi inti aplikasi
                    DashboardScreen()
                }
            }
        }
    }
}
