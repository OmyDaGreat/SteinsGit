package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/** Light color scheme inspired by Steins;Gate **/
private val LightColorScheme = lightColors(
  primary = Color(0xFF007ACC), // Cyan (Time machine interface)
  onPrimary = Color(0xFFFFFFFF), // White (text on primary)
  secondary = Color(0xFFFA8072), // Rusty Orange (Divergence meter)
  onSecondary = Color(0xFF000000), // Black
  background = Color(0xFFF8F9FA), // Near White
  onBackground = Color(0xFF212121), // Dark Gray text
  surface = Color(0xFFFFFFFF), // Pure White
  onSurface = Color(0xFF000000), // Black
)

/** Dark color scheme inspired by Steins;Gate **/
private val DarkColorScheme = darkColors(
  primary = Color(0xFF00BFFF), // Bright Cyan
  onPrimary = Color(0xFF003333), // Dark Cyan (text on primary)
  secondary = Color(0xFFFF6347), // Brighter Rusty Orange
  onSecondary = Color(0xFF330000), // Dark text
  background = Color(0xFF121212), // Deep Black
  onBackground = Color(0xFFE0E0E0), // Light Gray text
  surface = Color(0xFF1E1E1E), // Dark Surface
  onSurface = Color(0xFFECEFF1), // Off-White
)

/** Composable theme function based on Steins;Gate **/
@Composable
fun SteinsTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (!darkTheme) LightColorScheme else DarkColorScheme
  MaterialTheme(
    colors = colorScheme,
    content = content
  )
}
