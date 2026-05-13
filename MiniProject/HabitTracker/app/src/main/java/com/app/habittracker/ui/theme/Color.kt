package com.app.habittracker.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme

// --- DARK THEME: Charcoal Deep Blue ---
val DarkBGStart = Color(0xFF0F172A) // Deep Navy/Charcoal
val DarkBGEnd = Color(0xFF1E293B)   // Slate Blue/Grey
val DarkPrimary = Color(0xFF38BDF8)  // Sky Blue highlight
val DarkSecondary = Color(0xFF818CF8) // Indigo highlight
val DarkSurface = Color(0xFF1E293B)  // Slate
val DarkOnSurface = Color.White      // Pure White
val DarkOnSurfaceVariant = Color.White // Pure White
val DarkGlass = Color(0xFF1E293B)    // Match Achievement Surface
val DarkBorder = Color(0x3394A3B8)

// --- LIGHT THEME: Cream Ivory ---
val LightBGStart = Color(0xFFFFFDF5) // Creamy White
val LightBGEnd = Color(0xFFF8F4E1)   // Ivory shades
val LightPrimary = Color(0xFF0F172A) // Deep Dark Blue (Text/Primary)
val LightSecondary = Color(0xFF334155)
val LightSurface = Color(0xFFF1F5F9) // Slate White
val LightOnSurface = Color(0xFF0F172A) // Deep Black/Blue
val LightOnSurfaceVariant = Color(0xFF475569) // Darker Grey
val LightGlass = Color(0xFFF1F5F9)   // Match Achievement Surface
val LightBorder = Color(0x1A0F172A)

// --- REFINED GRADIENTS ---
val PremiumPrimaryGradient = listOf(Color(0xFF38BDF8), Color(0xFF818CF8)) // Sky to Indigo
val PremiumLightGradient = listOf(Color(0xFF0F172A), Color(0xFF334155))  // Deep Blue shades

val BossGradient = listOf(Color(0xFFEF4444), Color(0xFFB91C1C)) // Red Boss
val QuestGradient = listOf(Color(0xFF10B981), Color(0xFF059669)) // Green Quest

// M3 Color Scheme Helpers - LIGHT
val md_theme_light_primary = LightPrimary
val md_theme_light_onPrimary = Color.White
val md_theme_light_primaryContainer = Color(0xFFE2E8F0)
val md_theme_light_onPrimaryContainer = LightOnSurface
val md_theme_light_secondary = LightSecondary
val md_theme_light_onSecondary = Color.White
val md_theme_light_secondaryContainer = Color(0xFFF1F5F9)
val md_theme_light_onSecondaryContainer = LightOnSurface
val md_theme_light_tertiary = Color(0xFF0F172A)
val md_theme_light_onTertiary = Color.White
val md_theme_light_tertiaryContainer = Color(0xFFFDFCF0)
val md_theme_light_onTertiaryContainer = LightOnSurface
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color.White
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = LightBGStart
val md_theme_light_onBackground = LightOnSurface
val md_theme_light_surface = LightSurface
val md_theme_light_onSurface = LightOnSurface
val md_theme_light_surfaceVariant = Color(0xFFE2E8F0)
val md_theme_light_onSurfaceVariant = LightOnSurfaceVariant
val md_theme_light_outline = LightBorder
val md_theme_light_inverseOnSurface = Color(0xFFF1F0F4)
val md_theme_light_inverseSurface = Color(0xFF2F3033)
val md_theme_light_inversePrimary = Color(0xFF38BDF8)
val md_theme_light_surfaceTint = LightPrimary
val md_theme_light_outlineVariant = Color(0xFFC4C6D0)
val md_theme_light_scrim = Color.Black

// M3 Color Scheme Helpers - DARK
val md_theme_dark_primary = DarkPrimary
val md_theme_dark_onPrimary = Color.Black
val md_theme_dark_primaryContainer = Color(0xFF0F172A)
val md_theme_dark_onPrimaryContainer = Color.White
val md_theme_dark_secondary = DarkSecondary
val md_theme_dark_onSecondary = Color.White
val md_theme_dark_secondaryContainer = Color(0xFF1E293B)
val md_theme_dark_onSecondaryContainer = Color.White
val md_theme_dark_tertiary = DarkPrimary
val md_theme_dark_onTertiary = Color(0xFF0F172A)
val md_theme_dark_tertiaryContainer = Color(0xFF1E293B)
val md_theme_dark_onTertiaryContainer = Color.White
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = DarkBGStart
val md_theme_dark_onBackground = DarkOnSurface
val md_theme_dark_surface = DarkSurface
val md_theme_dark_onSurface = DarkOnSurface
val md_theme_dark_surfaceVariant = Color(0xFF334155)
val md_theme_dark_onSurfaceVariant = DarkOnSurfaceVariant
val md_theme_dark_outline = DarkBorder
val md_theme_dark_inverseOnSurface = DarkBGStart
val md_theme_dark_inverseSurface = DarkOnSurface
val md_theme_dark_inversePrimary = LightPrimary
val md_theme_dark_surfaceTint = DarkPrimary
val md_theme_dark_outlineVariant = Color(0xFF44474E)
val md_theme_dark_scrim = Color.Black

@Composable
fun getAppBackgroundGradient(): androidx.compose.ui.graphics.Brush {
    return if (isSystemInDarkTheme()) {
        androidx.compose.ui.graphics.Brush.verticalGradient(listOf(DarkBGStart, DarkBGEnd))
    } else {
        androidx.compose.ui.graphics.Brush.verticalGradient(listOf(LightBGStart, LightBGEnd))
    }
}

@Composable
fun getAppGlassColor(): Color {
    return MaterialTheme.colorScheme.surface
}

@Composable
fun getAppBorderColor(): Color {
    return MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
}

@Composable
fun getSignatureGradient(): List<Color> {
    return if (isSystemInDarkTheme()) PremiumPrimaryGradient else PremiumLightGradient
}

// Legacy support
val PrimaryGradient = PremiumPrimaryGradient
val DarkGlassColor = DarkSurface
val LightGlassColor = LightSurface
