package com.example.gastrovote.ui.theme


import android.app.Activity
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

private val EsquemaOscuro = darkColorScheme(
    primary = Color(0xFF8FB4E8),
    onPrimary = Color(0xFF0F1B2D),
    secondary = Color(0xFFF08A63),
    tertiary = Color(0xFFA6C47A),
    background = Color(0xFF121A26),
    surface = Color(0xFF1C2736),
    primaryContainer = Color(0xFF22324A),
    onPrimaryContainer = Color(0xFF8FB4E8),
    onBackground = Color(0xFFEDE8DD),
    onSurface = Color(0xFFEDE8DD),
    secondaryFixed = Color(0xFFC6E64A)
)

private val EsquemaClaro = lightColorScheme(
    primary = Color(0xFF1F4E8C),
    onPrimary = Color.White,
    secondary = Color(0xFFC8502D),
    tertiary = Color(0xFF5E7B3A),
    background = Color(0xFFFBF6EC),
    surface = Color.White,
    primaryContainer = Color(0xFFE6EEF8),
    onPrimaryContainer = Color(0xFF1F4E8C),
    onBackground = Color(0xFF1E2A3A),
    onSurface = Color(0xFF1E2A3A),
    secondaryFixed = Color(0xFFC6E64A)
)

@Composable
fun GastroVoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}