package com.expensetracker.app.ui.addtransaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.expensetracker.app.domain.model.ExpenseCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySelector(
    selectedCategory: String,
    isLandscape: Boolean,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLandscape) {
        BoxWithConstraints(
            modifier = modifier
        ) {
            val cardWidth = (maxWidth - 16.dp) / 3

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 3
            ) {
                ExpenseCategory.entries.forEach { expenseCategory ->
                    CategoryCard(
                        expenseCategory = expenseCategory,
                        selected = selectedCategory == expenseCategory.label,
                        modifier = Modifier.width(cardWidth),
                        onClick = {
                            onCategorySelected(expenseCategory.label)
                        }
                    )
                }
            }
        }
    } else {
        Box(modifier = modifier) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ExpenseCategory.entries.toList()) { expenseCategory ->
                    CategoryCard(
                        expenseCategory = expenseCategory,
                        selected = selectedCategory == expenseCategory.label,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onCategorySelected(expenseCategory.label)
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(28.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFFF8F5FC)
                            )
                        )
                    )
            )
        }
    }
}
