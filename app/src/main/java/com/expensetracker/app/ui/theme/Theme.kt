package com.expensetracker.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.Primary,
    onPrimary = AppColors.OnPrimary,
    outline = AppColors.Border,
    background = AppColors.DarkBackground,
    surface = AppColors.DarkSurface,
    onBackground = Color(0xFFF3EFFA),
    onSurface = Color(0xFFF3EFFA),
    onSurfaceVariant = AppColors.TextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = AppColors.OnPrimary,
    outline = AppColors.Border,
    background = AppColors.LightLavender,
    surface = AppColors.CardBackground,
    onBackground = AppColors.TextPrimary,
    onSurface = AppColors.TextPrimary,
    onSurfaceVariant = AppColors.TextSecondary
)

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else
                dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}