package com.example.ui.theme

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
    primary = GoldPrimaryDark,
    onPrimary = Color(0xFF3F2E00),
    primaryContainer = GoldContainerDark,
    onPrimaryContainer = GoldPrimaryDark,
    secondary = BurgundySecondaryDark,
    onSecondary = Color(0xFF4A0012),
    secondaryContainer = BurgundyContainerDark,
    onSecondaryContainer = BurgundySecondaryDark,
    tertiary = LiturgicalVioletDark,
    onTertiary = Color(0xFF2E124D),
    tertiaryContainer = VioletContainerDark,
    onTertiaryContainer = LiturgicalVioletDark,
    background = NightParchment,
    onBackground = TextLight,
    surface = NightParchmentSurface,
    onSurface = TextLight,
    surfaceVariant = NightParchmentSurfaceVariant,
    onSurfaceVariant = Color(0xFFD6C8B8),
    outline = Color(0xFF9E8E7E)
)

private val LightColorScheme = lightColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF291D00),
    primaryContainer = GoldContainer,
    onPrimaryContainer = Color(0xFF402E00),
    secondary = BurgundySecondary,
    onSecondary = Color.White,
    secondaryContainer = BurgundyContainer,
    onSecondaryContainer = Color(0xFF3B000C),
    tertiary = LiturgicalViolet,
    onTertiary = Color.White,
    tertiaryContainer = VioletContainer,
    onTertiaryContainer = Color(0xFF240A42),
    background = ParchmentLight,
    onBackground = TextDark,
    surface = ParchmentSurfaceLight,
    onSurface = TextDark,
    surfaceVariant = ParchmentSurfaceVariantLight,
    onSurfaceVariant = Color(0xFF5A4D40),
    outline = Color(0xFF8B7B6B)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our custom illuminated palette by default for strong identity
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
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

