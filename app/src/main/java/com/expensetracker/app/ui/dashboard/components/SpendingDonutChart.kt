package com.expensetracker.app.ui.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensetracker.app.domain.model.SpendingByCategory
import com.expensetracker.app.ui.theme.toColor
import com.expensetracker.app.util.PercentageFormatter
import kotlin.math.atan2
import kotlin.math.min

private fun calculateSweepAngle(
    itemTotal: Double,
    totalSpending: Double
): Float {
    return if (totalSpending > 0.0) {
        (itemTotal / totalSpending * 360.0).toFloat()
    } else {
        0f
    }
}

@Composable
fun SpendingDonutChart(
    spendingByCategory: List<SpendingByCategory>,
    centerText: String,
    modifier: Modifier = Modifier
) {

    var selectedIndex by remember(spendingByCategory) {
        mutableIntStateOf(0)
    }

    val selected = spendingByCategory.getOrNull(selectedIndex)

    val totalSpending = spendingByCategory.sumOf { it.total }

    val selectedPercentage = if (selected != null && totalSpending > 0.0) {
        selected.total / totalSpending * 100
    } else {
        0.0
    }

    val emptyChartColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .pointerInput(spendingByCategory) {
                    detectTapGestures { tap ->
                        val center = Offset(
                            size.width / 2f,
                            size.height / 2f
                        )
                        val dx = tap.x - center.x
                        val dy = tap.y - center.y
                        val distance = kotlin.math.sqrt(dx * dx + dy * dy)

                        val radius = min(size.width, size.height) / 2f
                        val strokeWidth = radius * 0.38f

                        if (distance !in (radius - strokeWidth)..radius) {
                            return@detectTapGestures
                        }

                        var angle = Math
                            .toDegrees(
                                atan2(dy.toDouble(), dx.toDouble())
                            )
                            .toFloat() + 90f

                        if (angle < 0f) angle += 360f

                        var accumulated = 0f
                        spendingByCategory.forEachIndexed { index, item ->
                            val sweep = calculateSweepAngle(
                                itemTotal = item.total,
                                totalSpending = totalSpending
                            )
                            if (angle >= accumulated &&
                                angle < accumulated + sweep
                            ) {
                                selectedIndex = index
                                return@detectTapGestures
                            }
                            accumulated += sweep
                        }
                    }
                }
        ) {
            val stroke = Stroke(
                width = size.minDimension * 0.19f,
                cap = StrokeCap.Butt
            )

            var startAngle = -90f

            if (spendingByCategory.isEmpty()) {
                val diameter = size.minDimension

                val topLeft = Offset(
                    (size.width - diameter) / 2f,
                    (size.height - diameter) / 2f
                )

                drawArc(
                    color = emptyChartColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = androidx.compose.ui.geometry.Size(diameter, diameter),
                    style = stroke
                )
            }

            spendingByCategory.forEach { item ->
                val sweepAngle = calculateSweepAngle(
                    itemTotal = item.total,
                    totalSpending = totalSpending
                )
                val diameter = size.minDimension
                val topLeft = Offset(
                    (size.width - diameter) / 2f,
                    (size.height - diameter) / 2f
                )

                drawArc(
                    color = item.category.toColor(),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = androidx.compose.ui.geometry.Size(diameter, diameter),
                    style = stroke
                )
                startAngle += sweepAngle
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selected != null) {
                Text(
                    text = selected.category.label,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = PercentageFormatter.format(selectedPercentage),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "No transactions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = centerText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
