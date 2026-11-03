package com.expensetracker.app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppColors {
    val Primary = Color(0xFF655080)
    val OnPrimary = Color(0xFFF3EFFA)
    val LightLavender = Color(0xFFF3EFFA)
    val Border = Color(0xFFB5A8CC)
    val CardBackground = Color(0xFFFCFAFF)

    val TextPrimary = Color(0xFF25212B)
    val TextSecondary = Color(0xFF514A5D)
    val DarkBackground = Color(0xFF121212)
    val DarkSurface = Color(0xFF1E1E1E)

    val ScreenGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD8C9ED),
            Color(0xFFE8DFF5),
            Color(0xFFF8F5FC),
        )
    )
}