package util.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

enum class ThemeType(private val lightColor: Colors, private val darkColor: Colors) {
  Mint(
    lightColors(background = Color.Mint, onBackground = Color.DarkGray),
    darkColors(background = Color.Black, onBackground = Color.Mint),
  ),
  Plain(
    lightColors(background = Color.White, onBackground = Color.Black),
    darkColors(background = Color.Black, onBackground = Color.White),
  ),
  Galaxy(
    lightColors(background = Color.Blue, onBackground = Color.White),
    darkColors(background = Color.Black, onBackground = Color.LightBlue),
  ),
  Sunset(
    lightColors(background = Color.Sunset, onBackground = Color.DarkGray),
    darkColors(background = Color.DarkGray, onBackground = Color.Sunset),
  ),
  Forest(
    lightColors(background = Color.ForestGreen, onBackground = Color.White),
    darkColors(background = Color.Black, onBackground = Color.ForestGreen),
  ),
  Lavender(
    lightColors(background = Color.Lavender, onBackground = Color.DarkLavender),
    darkColors(background = Color.DarkLavender, onBackground = Color.Lavender),
  ),
  Coral(
    lightColors(background = Color.Coral, onBackground = Color.White),
    darkColors(background = Color.Black, onBackground = Color.Coral),
  ),
  Sand(
    lightColors(background = Color.Sand, onBackground = Color.White),
    darkColors(background = Color.Black, onBackground = Color.Sand),
  );

  val colors: Colors
    @Composable get() = darkColor.takeIf { isSystemInDarkTheme() } ?: lightColor
}

@Stable
val Color.Companion.Mint: Color
  get() = Color(0xFF98FB98)

@Stable
val Color.Companion.Sunset: Color
  get() = Color(0xFFFF6347)

@Stable
val Color.Companion.ForestGreen: Color
  get() = Color(0xFF228B22)

@Stable
val Color.Companion.Lavender: Color
  get() = Color(0xFFE6E6FA)

@Stable
val Color.Companion.DarkLavender: Color
  get() = Color(0xff9b679b)

@Stable
val Color.Companion.Coral: Color
  get() = Color(0xFFFF7F50)

@Stable
val Color.Companion.Sand: Color
  get() = Color(0xFFC2B280)

@Stable
val Color.Companion.LightBlue: Color
  get() = Color(0xFFADD8FF)
