package com.expensetracker.app.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.expensetracker.app.domain.model.SpendingByCategory
import com.expensetracker.app.ui.components.CategoryIcon
import com.expensetracker.app.util.CurrencyFormatter
import com.expensetracker.app.util.PercentageFormatter

@Composable
fun SpendingCategoryList(
    spendingByCategory: List<SpendingByCategory>,
    modifier: Modifier = Modifier,
    scrollEnabled: Boolean = true
) {
    Column(
        modifier = modifier.then(
            if (scrollEnabled) {
                Modifier.verticalScroll(rememberScrollState())
            } else {
                Modifier
            }
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (spendingByCategory.isEmpty()) {
            Text(
                text = "No spending to display yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            spendingByCategory.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CategoryIcon(
                        category = item.category,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.category.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = PercentageFormatter.format(item.percentage),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = CurrencyFormatter.format(item.total),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}