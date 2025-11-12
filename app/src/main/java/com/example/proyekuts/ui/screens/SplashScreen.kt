package com.example.proyekuts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyekuts.R
import com.example.proyekuts.ui.theme.ProyekUTSTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Efek ini akan berjalan sekali saat komposabel pertama kali ditampilkan.
    // Setelah 5 detik (5000 milidetik), ia akan memanggil lambda onTimeout.
    LaunchedEffect(Unit) {
        delay(5000)
        onTimeout()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Judul Aplikasi
        Text(
            text = "UTS Pemrograman Mobile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Foto Diri (pastikan nama file sesuai, misal: R.drawable.my_photo)
        Image(
            painter = painterResource(id = R.drawable.foto_saya),
            contentDescription = "Foto Diri",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape) // Membuat gambar menjadi bulat
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Informasi Nama dan NIM
        Text(
            text = "Muhammad Iqbal Pasha Al Farabi",
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "152023174",
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Pratinjau untuk melihat tampilan Splash Screen di Android Studio
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ProyekUTSTheme {
        SplashScreen(onTimeout = {}) // onTimeout tidak melakukan apa-apa di pratinjau
    }
}
