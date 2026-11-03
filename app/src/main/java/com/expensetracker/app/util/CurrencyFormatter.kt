package com.expensetracker.app.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK)

    fun format(amount: Double): String {
        return currencyFormat.format(amount)
    }
}