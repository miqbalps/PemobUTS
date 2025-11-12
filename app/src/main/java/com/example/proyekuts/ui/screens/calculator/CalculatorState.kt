package com.example.proyekuts.ui.screens.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Sealed class untuk merepresentasikan semua aksi yang bisa dilakukan
sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
    object Clear : CalculatorAction()
    object Calculate : CalculatorAction()
    object Decimal : CalculatorAction()
    object ToggleSign : CalculatorAction() // Baru: untuk +/-
    object Percent : CalculatorAction() // Baru: untuk %
}

// Enum untuk operasi matematika
enum class CalculatorOperation(val symbol: String) {
    Add("+"),
    Subtract("-"),
    Multiply("x"),
    Divide("÷")
    // Square dan SquareRoot dihapus
}

// State Holder Class
class CalculatorState {
    var display by mutableStateOf("0") // Teks besar (hasil/input)
        private set
    var expression by mutableStateOf("") // Teks kecil (histori/ekspresi)
        private set

    private var number1: Double? = null
    private var number2: Double? = null
    private var operation: CalculatorOperation? = null
    private var justCalculated = false // Flag untuk menangani input setelah '='

    fun onAction(action: CalculatorAction) {
        // Jika baru saja menghitung, input angka baru akan me-reset
        if (justCalculated && action is CalculatorAction.Number) {
            clear(keepExpression = false)
        }
        justCalculated = false

        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Clear -> clear(keepExpression = false)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.ToggleSign -> toggleSign()
            is CalculatorAction.Percent -> percent()
        }
    }

    private fun enterNumber(number: Int) {
        if (display == "0") {
            display = number.toString()
        } else if (display.length < 15) {
            display += number.toString()
        }

        // Update angka yang relevan
        if (operation == null) {
            number1 = display.toDoubleOrNull()
        } else {
            number2 = display.toDoubleOrNull()
        }
    }

    private fun enterOperation(op: CalculatorOperation) {
        // Jika sudah ada nomor 1, 2, dan operasi, hitung dulu
        if (number1 != null && number2 != null) {
            performCalculation()
        }

        // Tetapkan operasi baru
        operation = op
        number1 = display.toDoubleOrNull() // Simpan angka saat ini sebagai number1
        expression = "${formatResult(number1!!)} ${op.symbol}" // Tampilkan di ekspresi
        display = "0" // Reset display untuk input number2
    }

    private fun performCalculation() {
        val n1 = number1 ?: return
        val n2 = display.toDoubleOrNull() ?: number2 ?: return // Ambil angka terakhir dari display
        val op = operation ?: return

        // Simpan ekspresi lengkap sebelum menghitung
        expression = "${formatResult(n1)} ${op.symbol} ${formatResult(n2)}"

        val result = when (op) {
            CalculatorOperation.Add -> n1 + n2
            CalculatorOperation.Subtract -> n1 - n2
            CalculatorOperation.Multiply -> n1 * n2
            CalculatorOperation.Divide -> if (n2 != 0.0) n1 / n2 else Double.NaN
        }

        if (result.isNaN()) {
            display = "Error"
        } else {
            display = formatResult(result)
        }

        // Siapkan untuk perhitungan selanjutnya
        number1 = result
        number2 = null
        operation = null
        justCalculated = true // Tandai bahwa kita baru saja menghitung
    }

    private fun clear(keepExpression: Boolean = false) {
        display = "0"
        if (!keepExpression) {
            expression = ""
        }
        number1 = null
        number2 = null
        operation = null
    }

    private fun toggleSign() {
        val value = display.toDoubleOrNull() ?: return
        display = formatResult(value * -1)

        if (operation == null) {
            number1 = display.toDoubleOrNull()
        } else {
            number2 = display.toDoubleOrNull()
        }
    }

    private fun percent() {
        val value = display.toDoubleOrNull() ?: return
        display = formatResult(value / 100.0)

        if (operation == null) {
            number1 = display.toDoubleOrNull()
        } else {
            number2 = display.toDoubleOrNull()
        }
    }

    private fun enterDecimal() {
        if (!display.contains(".")) {
            display += "."
        }
    }

    private fun formatResult(number: Double): String {
        return if (number == number.toLong().toDouble()) {
            number.toLong().toString()
        } else {
            number.toString()
        }
    }
}