package com.example.proyekuts.ui.screens.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyekuts.ui.theme.ProyekUTSTheme

// Warna kustom sesuai gambar
private val darkBlueBg = Color(0xFF013674)
private val buttonLightGray = Color(0xFFE8EDF2)
private val grayBG = Color(0xFFF5F7F8)
private val buttonWhite = Color.White
private val textDark = Color.Black
private val textWhite = Color.White

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val stateHolder = remember { CalculatorState() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(darkBlueBg) // Latar belakang utama adalah biru
    ) {
        // 1. Bagian Display (Biru)
        DisplaySection(
            expression = stateHolder.expression,
            display = stateHolder.display,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Mengisi sisa ruang di atas
        )

        // 2. Bagian Tombol (Putih)
        ButtonSection(
            onAction = stateHolder::onAction,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    grayBG, // Latar belakang keypad
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
        )
    }
}

@Composable
private fun DisplaySection(
    expression: String,
    display: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom // Teks menempel di bawah
    ) {
        // Teks ekspresi (kecil di atas)
        Text(
            text = expression,
            style = MaterialTheme.typography.headlineMedium,
            color = textWhite.copy(alpha = 0.7f), // Semi-transparan
            textAlign = TextAlign.End,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Teks display/hasil (besar di bawah)
        Text(
            text = display,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 72.sp, // Ukuran font besar
            fontWeight = FontWeight.Bold,
            color = textWhite,
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }
}

@Composable
private fun ButtonSection(
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp).padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar baris
    ) {
        val buttonHeight = 80.dp // Tinggi tombol yang seragam
        val buttonArrangement = Arrangement.spacedBy(12.dp) // Jarak antar tombol

        // Baris 1: AC, +/-, %, ÷
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = buttonArrangement
        ) {
            CalculatorButton(
                symbol = "AC",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Clear) }
            )
            CalculatorButton(
                symbol = "+/-",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.ToggleSign) }
            )
            CalculatorButton(
                symbol = "%",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Percent) }
            )
            CalculatorButton(
                symbol = "÷",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }
            )
        }

        // Baris 2: 7, 8, 9, x
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = buttonArrangement
        ) {
            CalculatorButton(
                symbol = "7",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(7)) }
            )
            CalculatorButton(
                symbol = "8",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(8)) }
            )
            CalculatorButton(
                symbol = "9",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(9)) }
            )
            CalculatorButton(
                symbol = "x",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }
            )
        }

        // Baris 3: 4, 5, 6, -
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = buttonArrangement
        ) {
            CalculatorButton(
                symbol = "4",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(4)) }
            )
            CalculatorButton(
                symbol = "5",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(5)) }
            )
            CalculatorButton(
                symbol = "6",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(6)) }
            )
            CalculatorButton(
                symbol = "-",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }
            )
        }

        // Baris 4: 1, 2, 3, +
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = buttonArrangement
        ) {
            CalculatorButton(
                symbol = "1",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(1)) }
            )
            CalculatorButton(
                symbol = "2",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(2)) }
            )
            CalculatorButton(
                symbol = "3",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Number(3)) }
            )
            CalculatorButton(
                symbol = "+",
                backgroundColor = buttonLightGray,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }
            )
        }

        // Baris 5: 0, ., =
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = buttonArrangement
        ) {
            CalculatorButton(
                symbol = "0",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(2f), // Tombol 0 lebih lebar
                alignment = Alignment.CenterStart, // Teks 0 rata kiri
                onClick = { onAction(CalculatorAction.Number(0)) }
            )
            CalculatorButton(
                symbol = ".",
                backgroundColor = buttonWhite,
                textColor = textDark,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Decimal) }
            )
            CalculatorButton(
                symbol = "=",
                backgroundColor = darkBlueBg, // Warna biru
                textColor = textWhite,
                modifier = Modifier.weight(1f),
                onClick = { onAction(CalculatorAction.Calculate) }
            )
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp)) // Bentuk "pil"
            .background(backgroundColor)
            .clickable { onClick() }
            .then(modifier)
            .height(80.dp) // Tinggi tombol seragam
            // Padding horizontal khusus untuk tombol '0'
            .padding(horizontal = if (alignment == Alignment.CenterStart) 30.dp else 0.dp)
    ) {
        Text(
            text = symbol,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    ProyekUTSTheme {
        CalculatorScreen()
    }
}