package com.expensetracker.app.ui.addtransaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensetracker.app.ui.theme.AppInputStyle
import com.expensetracker.app.ui.theme.AppShapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.expensetracker.app.util.localDateToPickerMillis
import com.expensetracker.app.util.pickerMillisToLocalTimestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDateSelector(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    showDatePicker: Boolean,
    onShowDatePickerChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowDatePickerChange(true) },
        shape = AppShapes.input,
        color = AppInputStyle.background,
        border = BorderStroke(1.dp, AppInputStyle.border)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 18.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = "Select date",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.UK
                ).format(Date(selectedDate)),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Open calendar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis =
            localDateToPickerMillis(selectedDate)
        )

        DatePickerDialog(
            onDismissRequest = {
                onShowDatePickerChange(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(
                                pickerMillisToLocalTimestamp(it)
                            )
                        }
                        onShowDatePickerChange(false)
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onShowDatePickerChange(false)
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

