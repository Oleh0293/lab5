package com.lab5.ui.theme

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
    primary = Color(0xFFBB86FC), // Фіолетовий для акцентів
    secondary = Color(0xFF03DAC6), // Бірюзовий для виділень
    tertiary = Color(0xFFCF6679), // Насичений рожевий
    background = Color(0xFF121212), // Темно-сірий фон
    surface = Color(0xFF1E1E1E), // Темно-сірий для карток
    onPrimary = Color.Black, // Чорний текст на акцентному кольорі
    onSecondary = Color.Black, // Чорний текст на бірюзовому
    onTertiary = Color.White, // Білий текст на рожевому
    onBackground = Color(0xFFE0E0E0), // Світло-сірий для тексту
    onSurface = Color(0xFFE0E0E0) // Світло-сірий текст на темних поверхнях
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Насичений фіолетовий для акцентів
    secondary = Color(0xFF03DAC6), // Бірюзовий для виділень
    tertiary = Color(0xFFFF5722), // Яскравий помаранчевий
    background = Color(0xFFF5F5F5), // Світло-сірий фон
    surface = Color.White, // Білий для карток
    onPrimary = Color.White, // Білий текст на фіолетовому
    onSecondary = Color.Black, // Чорний текст на бірюзовому
    onTertiary = Color.White, // Білий текст на помаранчевому
    onBackground = Color.Black, // Чорний текст на світлому фоні
    onSurface = Color.Black // Чорний текст на білих поверхнях
)

@Composable
fun Lab5Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
