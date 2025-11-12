package com.example.proyekuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.proyekuts.ui.screens.SplashScreen
import com.example.proyekuts.ui.theme.ProyekUTSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyekUTSTheme {
                var showSplashScreen by remember { mutableStateOf(true) }
                if (showSplashScreen) {
                    SplashScreen(onTimeout = { showSplashScreen = false })
                } else {
                    DashboardScreen()
                }
            }
        }
    }
}
