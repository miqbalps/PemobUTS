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

/**
 * Object to hold all custom colors and constants for the Calculator UI.
 */
private object CalcColors {
    val DarkBlueBg = Color(0xFF013674)
    val ButtonLightGray = Color(0xFFE8EDF2)
    val KeypadGrayBg = Color(0xFFF5F7F8)
    val ButtonWhite = Color.White
    val TextDark = Color.Black
    val TextWhite = Color.White
}

/**
 * Represents a single button on the calculator keypad.
 * @param symbol The text displayed on the button.
 * @param action The [CalculatorAction] to be performed on click.
 * @param backgroundColor The background color of the button.
 * @param textColor The text color of the button.
 * @param weight The layout weight of the button in a Row.
 * @param alignment The alignment of the text inside the button.
 */
private data class ButtonData(
    val symbol: String,
    val action: CalculatorAction,
    val backgroundColor: Color = CalcColors.ButtonWhite,
    val textColor: Color = CalcColors.TextDark,
    val weight: Float = 1f,
    val alignment: Alignment = Alignment.Center
)

/**
 * The main entry point for the Calculator feature.
 * This Composable orchestrates the display and button sections.
 */
@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val stateHolder = remember { CalculatorState() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CalcColors.DarkBlueBg)
    ) {
        DisplaySection(
            expression = stateHolder.expression,
            display = stateHolder.display,
            modifier = Modifier.weight(1f) // Fills the remaining space at the top
        )

        ButtonSection(
            onAction = stateHolder::onAction,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = CalcColors.KeypadGrayBg,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
        )
    }
}

/**
 * Displays the calculator's expression and result.
 */
@Composable
private fun DisplaySection(
    expression: String,
    display: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom // Aligns text to the bottom
    ) {
        Text(
            text = expression,
            style = MaterialTheme.typography.headlineMedium,
            color = CalcColors.TextWhite.copy(alpha = 0.7f),
            textAlign = TextAlign.End,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = display,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = CalcColors.TextWhite,
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }
}

/**
 * Lays out all the calculator buttons in a grid.
 */
@Composable
private fun ButtonSection(
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonRows = listOf(
        // Row 1
        listOf(
            ButtonData("AC", CalculatorAction.Clear, CalcColors.ButtonLightGray),
            ButtonData("+/-", CalculatorAction.ToggleSign, CalcColors.ButtonLightGray),
            ButtonData("%", CalculatorAction.Percent, CalcColors.ButtonLightGray),
            ButtonData("÷", CalculatorAction.Operation(CalculatorOperation.Divide), CalcColors.ButtonLightGray),
        ),
        // Row 2
        listOf(
            ButtonData("7", CalculatorAction.Number(7)),
            ButtonData("8", CalculatorAction.Number(8)),
            ButtonData("9", CalculatorAction.Number(9)),
            ButtonData("x", CalculatorAction.Operation(CalculatorOperation.Multiply), CalcColors.ButtonLightGray),
        ),
        // Row 3
        listOf(
            ButtonData("4", CalculatorAction.Number(4)),
            ButtonData("5", CalculatorAction.Number(5)),
            ButtonData("6", CalculatorAction.Number(6)),
            ButtonData("-", CalculatorAction.Operation(CalculatorOperation.Subtract), CalcColors.ButtonLightGray),
        ),
        // Row 4
        listOf(
            ButtonData("1", CalculatorAction.Number(1)),
            ButtonData("2", CalculatorAction.Number(2)),
            ButtonData("3", CalculatorAction.Number(3)),
            ButtonData("+", CalculatorAction.Operation(CalculatorOperation.Add), CalcColors.ButtonLightGray),
        ),
        // Row 5
        listOf(
            ButtonData("0", CalculatorAction.Number(0), weight = 2f, alignment = Alignment.CenterStart),
            ButtonData(".", CalculatorAction.Decimal),
            ButtonData("=", CalculatorAction.Calculate, CalcColors.DarkBlueBg, CalcColors.TextWhite),
        )
    )

    Column(
        modifier = modifier.padding(16.dp).padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        buttonRows.forEach { rowData ->
            ButtonRow(rowData = rowData, onAction = onAction)
        }
    }
}

/**
 * Creates a single row of calculator buttons.
 */
@Composable
private fun ButtonRow(
    rowData: List<ButtonData>,
    onAction: (CalculatorAction) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        rowData.forEach { button ->
            CalculatorButton(
                symbol = button.symbol,
                backgroundColor = button.backgroundColor,
                textColor = button.textColor,
                modifier = Modifier.weight(button.weight),
                alignment = button.alignment,
                onClick = { onAction(button.action) }
            )
        }
    }
}

/**
 * The base Composable for a single calculator button.
 */
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
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .then(modifier)
            .height(80.dp)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorScreenPreview() {
    ProyekUTSTheme {
        CalculatorScreen()
    }
}
