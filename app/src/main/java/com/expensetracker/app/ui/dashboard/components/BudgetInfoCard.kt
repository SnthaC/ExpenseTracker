package com.expensetracker.app.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.expensetracker.app.ui.theme.AppColors
import com.expensetracker.app.ui.theme.AppShapes
import com.expensetracker.app.util.CurrencyFormatter

@Composable
fun BudgetInfoCard(
    monthlyBudget: Double?,
    totalSpending: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = AppShapes.card,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.LightLavender
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (monthlyBudget != null && monthlyBudget > 0.0) {
                val remaining = monthlyBudget - totalSpending
                val percentage = totalSpending / monthlyBudget * 100

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Total budget",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant                        )
                        Text(
                            text = CurrencyFormatter.format(monthlyBudget),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(40.dp)
                            .background(Color(0xFFDED9E8))
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = if (remaining >= 0) "Remaining" else "Over budget",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = CurrencyFormatter.format(kotlin.math.abs(remaining)),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (remaining < 0) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${"%.1f".format(percentage)}% of budget used",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    LinearProgressIndicator(
                        progress = { (percentage / 100).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = if (percentage >= 100) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        trackColor = Color(0xFFDED9E8),
                        strokeCap = StrokeCap.Round
                    )
                }

            } else {
                Text(
                    text = "No monthly budget set. Set one in the Budget tab.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}