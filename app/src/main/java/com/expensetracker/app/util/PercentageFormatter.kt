package com.expensetracker.app.util

import java.util.Locale

object PercentageFormatter {

    fun format(value: Number): String =
        String.format(Locale.UK, "%.2f%%", value.toDouble())
}