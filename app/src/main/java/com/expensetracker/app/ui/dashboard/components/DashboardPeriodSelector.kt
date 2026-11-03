package com.expensetracker.app.ui.dashboard.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.expensetracker.app.ui.dashboard.DashboardPeriod
import com.expensetracker.app.ui.theme.AppColors

@Composable
fun DashboardPeriodSelector(
    selectedPeriod: DashboardPeriod,
    onPeriodSelected: (DashboardPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        DashboardPeriod.entries.forEachIndexed { index, period ->
            SegmentedButton(
                selected = selectedPeriod == period,
                onClick = {
                    onPeriodSelected(period)
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = DashboardPeriod.entries.size
                ),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AppColors.LightLavender,
                    inactiveContainerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = period.label,
                    color = if (selectedPeriod == period) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    fontWeight = if (selectedPeriod == period) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}