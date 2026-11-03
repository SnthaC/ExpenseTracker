package com.expensetracker.app.ui.addtransaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.expensetracker.app.domain.model.ExpenseCategory
import com.expensetracker.app.ui.components.CategoryIcon
import com.expensetracker.app.ui.theme.toColor

@Composable
fun CategoryCard(
    expenseCategory: ExpenseCategory,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val categoryColor = expenseCategory.toColor()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) categoryColor.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surface
            )
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) categoryColor
                else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    categoryColor.copy(alpha = if (selected) 0.30f else 0.18f),
                    RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            CategoryIcon(
                category = expenseCategory,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = expenseCategory.label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}