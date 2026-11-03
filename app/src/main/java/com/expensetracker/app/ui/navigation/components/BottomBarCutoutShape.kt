package com.expensetracker.app.ui.navigation.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class BottomBarCutoutShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val radius = with(density) { 39.dp.toPx() }
        val center = size.width / 2f

        path.moveTo(0f, 0f)
        path.lineTo(center - radius, 0f)

        path.arcTo(
            rect = androidx.compose.ui.geometry.Rect(
                left = center - radius,
                top = -radius,
                right = center + radius,
                bottom = radius
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -180f,
            forceMoveTo = false
        )

        path.lineTo(size.width, 0f)
        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
        path.close()

        return Outline.Generic(path)
    }
}