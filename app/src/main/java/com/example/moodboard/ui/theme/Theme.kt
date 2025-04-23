package com.example.moodboard.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = prussianBlue,
    onPrimary = whiteBackground,
    secondary = yellowChartreuse,
    onSecondary = prussianBlue,
    background = whiteBackground,
    onBackground = textBlack,
    surface = whiteBackground,
    onSurface = prussianBlue,
    tertiary = redBittersweet,
    surfaceVariant = columbiaBlue,
    primaryContainer = containerColor
)

private val LightColorScheme = lightColorScheme(
    primary = prussianBlue,
    onPrimary = whiteBackground,
    secondary = yellowChartreuse,
    onSecondary = prussianBlue,
    background = whiteBackground,
    onBackground = Color(0xFF212121),
    surface = whiteBackground,
    onSurface = prussianBlue,
    tertiary = redBittersweet,
    surfaceVariant = columbiaBlue
)

@Composable
fun MoodBoardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)

            window.statusBarColor = colorScheme.onBackground.toArgb()

            window.navigationBarColor = colorScheme.onBackground.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}