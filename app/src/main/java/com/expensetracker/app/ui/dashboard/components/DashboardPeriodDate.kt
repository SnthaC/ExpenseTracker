package com.expensetracker.app.ui.dashboard.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.expensetracker.app.ui.dashboard.DashboardPeriod
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DashboardPeriodDate(
    selectedPeriod: DashboardPeriod,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val month = today.format(
        DateTimeFormatter.ofPattern("MMMM", Locale.UK)
    )

    if (selectedPeriod == DashboardPeriod.TODAY) {
        Text(
            text = "Today, ${today.dayOfMonth} $month",
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 21.sp
            )
        )
    } else {
        Text(
            text = month,
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 21.sp,
            )
        )
    }
}