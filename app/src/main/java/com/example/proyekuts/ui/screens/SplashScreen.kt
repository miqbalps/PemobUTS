package com.example.proyekuts.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyekuts.R
import com.example.proyekuts.ui.theme.ProyekUTSTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val darkBlueBg = Color(0xFF013773)
    val lightBlue = Color(0xFF4A90E2)
    val darkBlueGradient = Brush.verticalGradient(
        colors = listOf(lightBlue, darkBlueBg, Color(0xFF002145))
    )

    LaunchedEffect(Unit) {
        delay(5000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlueGradient)
    ) {
        // Background geometric patterns
        GeometricBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main Title
            Text(
                text = "UTS Pemrograman Mobile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                modifier = Modifier.offset(y = (-5).dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Info Card
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.foto_saya),
                        contentDescription = "Foto Diri Kecil",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Muhammad Iqbal Pasha",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "152023174",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

@Composable
fun GeometricBackground() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val transparentWhite = Color.White.copy(alpha = 0.05f)
        val transparentWhite2 = Color.White.copy(alpha = 0.03f)

        // Large circles
        drawCircle(
            color = transparentWhite,
            center = Offset(canvasWidth * 0.8f, canvasHeight * 0.2f),
            radius = 120.dp.toPx()
        )

        drawCircle(
            color = transparentWhite2,
            center = Offset(canvasWidth * 0.2f, canvasHeight * 0.7f),
            radius = 150.dp.toPx()
        )

        // Triangles
        drawRect(
            color = transparentWhite,
            topLeft = Offset(canvasWidth * 0.1f, canvasHeight * 0.1f),
            size = Size(80.dp.toPx(), 80.dp.toPx())
        )

        // Rotated squares
        rotate(45f) {
            drawRect(
                color = transparentWhite2,
                topLeft = Offset(canvasWidth * 0.7f, canvasHeight * 0.6f),
                size = Size(60.dp.toPx(), 60.dp.toPx())
            )
        }

        // Lines and dots pattern
        for (i in 0..5) {
            val y = canvasHeight * 0.1f + i * 80.dp.toPx()
            drawLine(
                color = transparentWhite,
                start = Offset(0f, y),
                end = Offset(canvasWidth, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Dots grid
        for (i in 0..8) {
            for (j in 0..12) {
                val x = canvasWidth * 0.1f + i * 40.dp.toPx()
                val y = canvasHeight * 0.3f + j * 40.dp.toPx()
                drawCircle(
                    color = transparentWhite2,
                    center = Offset(x, y),
                    radius = 2.dp.toPx()
                )
            }
        }

        // Abstract shapes in corners
        drawCircle(
            color = transparentWhite,
            center = Offset(canvasWidth * 0.9f, canvasHeight * 0.9f),
            radius = 40.dp.toPx()
        )

        drawRect(
            color = transparentWhite2,
            topLeft = Offset(canvasWidth * 0.05f, canvasHeight * 0.85f),
            size = Size(50.dp.toPx(), 50.dp.toPx())
        )

        // Hexagon pattern
        drawRect(
            color = transparentWhite,
            topLeft = Offset(canvasWidth * 0.8f, canvasHeight * 0.05f),
            size = Size(30.dp.toPx(), 30.dp.toPx())
        )

        // Diagonal lines
        for (i in -2..2) {
            val startX = i * 100.dp.toPx()
            drawLine(
                color = transparentWhite2,
                start = Offset(startX, 0f),
                end = Offset(startX + canvasWidth, canvasHeight),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ProyekUTSTheme {
        SplashScreen(onTimeout = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GeometricBackgroundPreview() {
    ProyekUTSTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF013773))
        ) {
            GeometricBackground()
        }
    }
}